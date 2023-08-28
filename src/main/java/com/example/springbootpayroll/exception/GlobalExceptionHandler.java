package com.example.springbootpayroll.exception;

import com.example.springbootpayroll.model.dto.response.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception e, final WebRequest request) {
        log.error("handleAll ", e);

        ResponseError responseError = new ResponseError(500, e.getMessage());
        return handleExceptionInternal(e, responseError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseError> handlerNotFoundError(NotFoundException e) {
        log.error("handle NotFoundError", e);
        ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = IsExistsException.class)
    public ResponseEntity<ResponseError> handlerIsExistsException(IsExistsException e) {
        log.error("handle IsExistException", e);
        ResponseError responseError = new ResponseError(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseError> handlerBadRequestException(BadRequestException e) {
        log.error("handle BadRequestException", e);
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error("handle MethodArgumentNotValidException", ex);
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.value(), errors.toString());
        return createResponseEntity(responseError, headers, HttpStatus.BAD_REQUEST, request);
    }
}


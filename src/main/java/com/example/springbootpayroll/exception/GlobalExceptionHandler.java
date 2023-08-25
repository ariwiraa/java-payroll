package com.example.springbootpayroll.exception;

import com.example.springbootpayroll.model.dto.response.ResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception e, final WebRequest request) {
        ResponseError responseError = new ResponseError(500, e.getMessage());
        return handleExceptionInternal(e, responseError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseError> handlerNotFoundError(NotFoundException e) {
        ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = IsExistsException.class)
    public ResponseEntity<ResponseError> handlerIsExistsException(IsExistsException e) {
        ResponseError responseError = new ResponseError(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseError> handlerBadRequestException(BadRequestException e) {
        ResponseError responseError = new ResponseError(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }
}

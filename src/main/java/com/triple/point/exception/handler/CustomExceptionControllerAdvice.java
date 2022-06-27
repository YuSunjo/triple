package com.triple.point.exception.handler;

import com.triple.point.ApiResponse;
import com.triple.point.exception.customException.ConflictException;
import com.triple.point.exception.customException.NotFoundException;
import com.triple.point.exception.customException.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiResponse<Object> handlerNotFoundException(NotFoundException e) {
        return ApiResponse.error(e.getErrorCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<Object> handlerValidationException(ValidationException e) {
        return ApiResponse.error(e.getErrorCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ApiResponse<Object> handlerConflictException(ConflictException e) {
        return ApiResponse.error(e.getErrorCode().getCode(), e.getMessage());
    }

}

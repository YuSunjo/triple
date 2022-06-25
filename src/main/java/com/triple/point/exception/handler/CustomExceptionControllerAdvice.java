package com.triple.point.exception.handler;

import com.triple.point.ApiResponse;
import com.triple.point.exception.customException.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    protected ApiResponse<Object> handlerNotFound(NotFoundException e) {
        return ApiResponse.error(e.getErrorCode().getCode(), e.getMessage());
    }

}

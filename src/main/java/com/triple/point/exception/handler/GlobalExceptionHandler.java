package com.triple.point.exception.handler;

import com.triple.point.ApiResponse;
import com.triple.point.exception.errorCode.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<Object> handleEnum(HttpMessageNotReadableException e) {
        return ApiResponse.error(ErrorCode.VALIDATION_EXCEPTION.getCode(), ErrorCode.VALIDATION_EXCEPTION.getMessage());
    }

}

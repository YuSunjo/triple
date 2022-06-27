package com.triple.point.exception.handler;

import com.triple.point.ApiResponse;
import com.triple.point.exception.errorCode.ErrorCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ApiResponse<Object> handleEnum(HttpMessageNotReadableException e) {
        return ApiResponse.error(ErrorCode.ENUM_VALIDATION_EXCEPTION.getCode(), ErrorCode.ENUM_VALIDATION_EXCEPTION.getMessage());
    }

}

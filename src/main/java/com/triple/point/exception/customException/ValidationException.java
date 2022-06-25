package com.triple.point.exception.customException;

import com.triple.point.exception.errorCode.ErrorCode;

public class ValidationException extends CustomException {

    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_EXCEPTION);
    }

    public ValidationException() {
        super(ErrorCode.VALIDATION_EXCEPTION.getMessage(), ErrorCode.VALIDATION_EXCEPTION);
    }

}

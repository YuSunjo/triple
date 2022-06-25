package com.triple.point.exception.customException;

import com.triple.point.exception.errorCode.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND_EXCEPTION);
    }

}

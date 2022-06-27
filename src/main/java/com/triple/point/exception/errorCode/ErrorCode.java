package com.triple.point.exception.errorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {

    ENUM_VALIDATION_EXCEPTION("401", "잘못된 ENUM 입니다."),
    VALIDATION_EXCEPTION("400", "잘못된 입력입니다."),
    NOT_FOUND_EXCEPTION("404", "존재하지 않습니다."),
    CONFLICT_EXCEPTION("409", "이미 존재합니다.")
    ;

    private final String code;
    private final String message;

}

package com.smunity.exception;

import com.smunity.exception.code.AuthErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthException extends RuntimeException {

    private final String message;
    private final AuthErrorCode errorCode;

    @Override
    public String getMessage() {
        return String.format("%s\n[%s] %s", message, errorCode.getCode(), errorCode.getMessage());
    }
}

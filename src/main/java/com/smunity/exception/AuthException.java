package com.smunity.exception;

import com.smunity.exception.code.AuthErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AuthException extends RuntimeException {

    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return "[%s] %s".formatted(errorCode.getCode(), errorCode.getMessage());
    }
}

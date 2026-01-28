package com.smunity.exception;

import com.smunity.exception.code.AuthErrorCode;

public class AuthServerException extends AuthException {

    public AuthServerException(AuthErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}

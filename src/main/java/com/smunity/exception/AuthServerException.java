package com.smunity.exception;

import com.smunity.exception.code.AuthErrorCode;

public class AuthServerException extends AuthException {

    public AuthServerException(String message, AuthErrorCode errorCode) {
        super(message, errorCode);
    }
}

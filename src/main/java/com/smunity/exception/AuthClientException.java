package com.smunity.exception;

import com.smunity.exception.code.AuthErrorCode;

public class AuthClientException extends AuthException {

    public AuthClientException(String message, AuthErrorCode errorCode) {
        super(message, errorCode);
    }
}

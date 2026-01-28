package com.smunity.exception;

import com.smunity.exception.code.AuthErrorCode;

public class AuthClientException extends AuthException {

    public AuthClientException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}

package com.smunity.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode {

    // Auth Errors
    AUTH_UNAUTHORIZED(401, "AUTH001", "학번 및 비밀번호가 일치하지 않습니다."),
    AUTH_LOGIN_FAIL(401, "AUTH002", "샘물포털 로그인에 실패했습니다."),
    AUTH_FETCH_FAILURE(401, "AUTH003", "인증 서버 에러, 관리자에게 문의 바랍니다."),
    AUTH_EXCEEDED_LOGIN_ATTEMPTS(401, "AUTH004", "로그인 실패 5회 초과, 샘물 포탈을 통해 비밀번호 초기화를 진행해주시기 바랍니다.");

    private final int value;
    private final String code;
    private final String message;
}

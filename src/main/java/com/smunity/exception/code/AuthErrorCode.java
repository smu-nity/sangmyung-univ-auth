package com.smunity.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode {

    // Auth Errors
    AUTH_UNAUTHORIZED(401, "AUTH001", "학번 및 비밀번호가 일치하지 않습니다."),
    AUTH_EXCEEDED_LOGIN_ATTEMPTS(423, "AUTH002", "로그인 실패 5회 초과, 샘물 포탈을 통해 비밀번호 초기화를 진행해주시기 바랍니다."),
    SMU_LOGIN_FAILURE(503, "SMU001", "샘물포털 인증 서버에 일시적으로 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."),
    SMU_FETCH_FAILURE(503, "SMU002", "샘물포털 서버에서 데이터를 불러올 수 없습니다. 잠시 후 다시 시도해 주세요."),
    SMU_LOGIN_TIMEOUT(504, "SMU003", "샘물포털 인증 서버 응답 시간이 초과되었습니다. 잠시 후 다시 시도해 주세요."),
    SMU_FETCH_TIMEOUT(504, "SMU004", "샘물포털 서버 응답 시간이 초과되었습니다. 잠시 후 다시 시도해 주세요.");

    private final int value;
    private final String code;
    private final String message;
}

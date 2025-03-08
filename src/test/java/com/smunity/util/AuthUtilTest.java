package com.smunity.util;

import com.smunity.dto.AuthResponseDto;
import com.smunity.exception.AuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.smunity.exception.code.AuthErrorCode.AUTH_UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthUtilTest {

    @Test
    @DisplayName("인증 성공 시 응답 반환")
    public void authenticateSuccess() {
        // given
        String username = "201911019";
        String password = System.getenv("PASSWORD");

        // when
        AuthResponseDto responseDto = AuthUtil.authenticate(username, password);

        // then
        assertNotNull(responseDto);
        assertEquals("201911019", responseDto.username());
        assertEquals("최현민", responseDto.name());
        assertEquals("컴퓨터과학전공", responseDto.department());
        assertEquals("hyunmin-choi@naver.com", responseDto.email());
    }

    @Test
    @DisplayName("인증 실패 시 예외 발생")
    public void authenticateFailure() {
        // given
        String username = "201911019";
        String password = "password";

        // when & then
        AuthException exception = Assertions.assertThrows(AuthException.class, () -> {
            AuthUtil.authenticate(username, password);
        });
        Assertions.assertEquals(exception.getErrorCode(), AUTH_UNAUTHORIZED);
    }
}

package com.smunity;

import com.smunity.dto.AuthCourseResponseDto;
import com.smunity.dto.AuthResponseDto;
import com.smunity.exception.AuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.smunity.exception.code.AuthErrorCode.AUTH_UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.*;

class AuthManagerTest {

    @Test
    @DisplayName("올바른 인증 정보로 로그인 시 인증 응답 반환")
    public void authenticateSuccess() {
        // given
        String username = "201911019";
        String password = System.getenv("PASSWORD");

        // when
        AuthResponseDto responseDto = AuthManager.authenticate(username, password);

        // then
        assertNotNull(responseDto);
        assertEquals("201911019", responseDto.username());
        assertEquals("최현민", responseDto.name());
        assertEquals("hyunmin-choi@naver.com", responseDto.email());
        assertEquals("컴퓨터과학전공", responseDto.department());
        assertNull(responseDto.secondDepartment());
    }

    @Test
    @DisplayName("잘못된 인증 정보로 로그인 시 예외 발생")
    public void authenticateFailure() {
        // given
        String username = "201911019";
        String password = "password";

        // when & then
        AuthException exception = Assertions.assertThrows(AuthException.class, () -> {
            AuthManager.authenticate(username, password);
        });
        Assertions.assertEquals(AUTH_UNAUTHORIZED, exception.getErrorCode());
    }

    @Test
    @DisplayName("올바른 인증 정보로 수강 과목 조회 시 응답 반환")
    public void readCoursesSuccess() {
        // given
        String username = "201911019";
        String password = System.getenv("PASSWORD");

        // when
        AuthCourseResponseDto responseDto = AuthManager.readCourses(username, password);

        // then
        assertNotNull(responseDto);
        assertEquals(46, responseDto.count());
        assertEquals(130, responseDto.status().total());
        assertEquals(132, responseDto.status().completed());
        assertEquals(0, responseDto.status().required());
        assertEquals(100, responseDto.status().completion());
    }

    @Test
    @DisplayName("잘못된 인증 정보로 수강 과목 조회 시 예외 발생")
    public void readCoursesFailure() {
        // given
        String username = "201911019";
        String password = "password";

        // when & then
        AuthException exception = Assertions.assertThrows(AuthException.class, () -> {
            AuthManager.authenticate(username, password);
        });
        Assertions.assertEquals(AUTH_UNAUTHORIZED, exception.getErrorCode());
    }
}

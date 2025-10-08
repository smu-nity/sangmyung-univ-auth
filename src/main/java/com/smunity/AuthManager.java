package com.smunity;

import com.smunity.dto.AuthCourseResponseDto;
import com.smunity.dto.AuthResponseDto;
import com.smunity.util.FetchUtil;
import org.json.JSONObject;

/**
 * 학생 인증 및 이수 과목 조회 기능을 제공하는 클래스
 *
 * @author hyunmin0317
 */
public class AuthManager {

    /**
     * 학생 인증 메서드
     *
     * @param username 학번
     * @param password 비밀번호
     * @return 인증 결과를 담은 {@link AuthResponseDto} 객체
     */
    public static AuthResponseDto authenticate(String username, String password) {
        JSONObject response = FetchUtil.fetchInfo(username, password);
        return AuthResponseDto.from(response);
    }

    /**
     * 이수 과목 조회 메서드
     *
     * @param username 학번
     * @param password 비밀번호
     * @return 이수 과목 정보를 담은 {@link AuthCourseResponseDto} 객체
     */
    public static AuthCourseResponseDto readCourses(String username, String password) {
        JSONObject response = FetchUtil.fetchCourses(username, password);
        return AuthCourseResponseDto.from(response);
    }
}

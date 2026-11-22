package com.smunity.dto;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseDtoTest {

    @Test
    @DisplayName("복수전공이 없으면 secondDepartment는 null, teachingTrack은 false")
    void noSecondDepartment() {
        AuthResponseDto dto = AuthResponseDto.from(response("컴퓨터과학전공", ""));

        assertEquals("컴퓨터과학전공", dto.department());
        assertNull(dto.secondDepartment());
        assertFalse(dto.teachingTrack());
    }

    @Test
    @DisplayName("일반 복수전공은 학과명을 그대로 반환하고 teachingTrack은 false")
    void normalSecondDepartment() {
        AuthResponseDto dto = AuthResponseDto.from(response("컴퓨터과학전공", "경영학부"));

        assertEquals("경영학부", dto.secondDepartment());
        assertFalse(dto.teachingTrack());
    }

    @Test
    @DisplayName("(교직) 복수전공은 접두어를 제거하고 teachingTrack은 true")
    void teachingSecondDepartment() {
        AuthResponseDto dto = AuthResponseDto.from(response("컴퓨터과학전공", "(교직)국어교육과"));

        assertEquals("국어교육과", dto.secondDepartment());
        assertTrue(dto.teachingTrack());
    }

    @Test
    @DisplayName("단과대 접두어가 붙은 (교직) 복수전공도 정상 처리")
    void teachingSecondDepartmentWithCollege() {
        AuthResponseDto dto = AuthResponseDto.from(response("컴퓨터과학전공", "사범대학 (교직)수학교육과"));

        assertEquals("수학교육과", dto.secondDepartment());
        assertTrue(dto.teachingTrack());
    }

    private JSONObject response(String department, String secondDepartment) {
        JSONObject student = new JSONObject()
                .put("STDNO", "201911019")
                .put("NM_KOR", "홍길동")
                .put("EMAIL", "test@example.com")
                .put("TMP_DEPT_MJR_NM", department)
                .put("MJR_CD2", secondDepartment);
        return new JSONObject().put("dsStdInfoList", new JSONArray().put(student));
    }
}

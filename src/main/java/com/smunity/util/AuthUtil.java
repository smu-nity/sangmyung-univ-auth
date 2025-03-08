package com.smunity.util;

import com.smunity.dto.AuthCourseResponseDto;
import com.smunity.dto.AuthResponseDto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class AuthUtil {

    public static AuthResponseDto authenticate(String username, String password) {
        JSONObject response = FetchUtil.fetchInfo(username, password);
        return AuthResponseDto.from(response);
    }

    public List<AuthCourseResponseDto> readCourses(String username, String password) {
        JSONArray response = FetchUtil.fetchCourses(username, password);
        return AuthCourseResponseDto.from(response);
    }
}

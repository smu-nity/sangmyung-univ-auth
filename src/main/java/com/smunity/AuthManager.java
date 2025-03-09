package com.smunity;

import com.smunity.dto.AuthCourseResponseDto;
import com.smunity.dto.AuthResponseDto;
import com.smunity.util.FetchUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class AuthManager {

    public static AuthResponseDto authenticate(String username, String password) {
        JSONObject response = FetchUtil.fetchInfo(username, password);
        return AuthResponseDto.from(response);
    }

    public static List<AuthCourseResponseDto> readCourses(String username, String password) {
        JSONArray response = FetchUtil.fetchCourses(username, password);
        return AuthCourseResponseDto.from(response);
    }
}

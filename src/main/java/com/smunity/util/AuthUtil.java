package com.smunity.util;

import com.smunity.dto.AuthResponseDto;
import org.json.JSONObject;

public class AuthUtil {

    public static AuthResponseDto authenticate(String username, String password) {
        JSONObject response = FetchUtil.fetchInfo(username, password);
        return AuthResponseDto.from(response);
    }
}

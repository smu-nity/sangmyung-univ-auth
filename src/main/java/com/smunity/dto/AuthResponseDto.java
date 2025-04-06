package com.smunity.dto;

import lombok.Builder;
import org.json.JSONObject;

@Builder
public record AuthResponseDto(
        String username,
        String name,
        String department,
        String email
) {

    public static AuthResponseDto from(JSONObject obj) {
        return AuthResponseDto.builder()
                .username(obj.getString("STDNO"))
                .name(obj.getString("NM_KOR"))
                .department(getDepartment(obj.getString("TMP_DEPT_MJR_NM")))
                .email(obj.getString("EMAIL"))
                .build();
    }

    private static String getDepartment(String dept) {
        String[] depts = dept.split(" ");
        return depts[depts.length - 1];
    }
}

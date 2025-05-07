package com.smunity.dto;

import lombok.Builder;
import org.json.JSONObject;

@Builder
public record AuthResponseDto(
        String username,
        String name,
        String email,
        String department,
        String secondDepartment,
        boolean isDoubleMajor
) {

    public static AuthResponseDto from(JSONObject obj) {
        String secondDepartment = getSecondDepartment(obj.optString("MJR_CD2"));
        return AuthResponseDto.builder()
                .username(obj.getString("STDNO"))
                .name(obj.getString("NM_KOR"))
                .email(obj.getString("EMAIL"))
                .department(getDepartment(obj.getString("TMP_DEPT_MJR_NM")))
                .secondDepartment(secondDepartment)
                .isDoubleMajor(secondDepartment != null)
                .build();
    }

    private static String getDepartment(String dept) {
        String[] depts = dept.split(" ");
        return depts[depts.length - 1];
    }

    private static String getSecondDepartment(String dept) {
        return dept.isBlank() ? null : getDepartment(dept);
    }
}

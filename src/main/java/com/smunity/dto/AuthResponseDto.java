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
        boolean teachingTrack
) {

    private static final String TEACHING_PREFIX = "(교직)";

    public static AuthResponseDto from(JSONObject obj) {
        return of(obj.getJSONArray("dsStdInfoList").getJSONObject(0));
    }

    private static AuthResponseDto of(JSONObject obj) {
        String secondDepartment = getSecondDepartment(obj.optString("MJR_CD2"));
        return AuthResponseDto.builder()
                .username(obj.getString("STDNO"))
                .name(obj.getString("NM_KOR"))
                .email(obj.getString("EMAIL"))
                .department(getDepartment(obj.getString("TMP_DEPT_MJR_NM")))
                .secondDepartment(removeTeachingPrefix(secondDepartment))
                .teachingTrack(isTeachingTrack(secondDepartment))
                .build();
    }

    private static String getDepartment(String dept) {
        String[] depts = dept.split(" ");
        return depts[depts.length - 1];
    }

    private static String getSecondDepartment(String dept) {
        return dept.isBlank() ? null : getDepartment(dept);
    }

    private static boolean isTeachingTrack(String dept) {
        return dept != null && dept.startsWith(TEACHING_PREFIX);
    }

    private static String removeTeachingPrefix(String dept) {
        return isTeachingTrack(dept) ? dept.substring(TEACHING_PREFIX.length()).trim() : dept;
    }
}

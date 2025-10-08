package com.smunity.dto;

import lombok.Builder;
import org.json.JSONObject;

import java.util.List;

@Builder
public record AuthCourseResponseDto(
        int count,
        List<CourseResponseDto> content
) {

    public static AuthCourseResponseDto from(JSONObject obj) {
        List<CourseResponseDto> content = CourseResponseDto.from(obj);
        return AuthCourseResponseDto.builder()
                .count(content.size())
                .content(content)
                .build();
    }
}

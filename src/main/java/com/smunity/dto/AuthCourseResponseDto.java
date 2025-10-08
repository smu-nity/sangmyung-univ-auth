package com.smunity.dto;

import lombok.Builder;
import org.json.JSONObject;

import java.util.List;

@Builder
public record AuthCourseResponseDto(
        int count,
        List<CourseResponseDto> content,
        StatusResponseDto status
) {

    public static AuthCourseResponseDto from(JSONObject obj) {
        List<CourseResponseDto> content = CourseResponseDto.from(obj);
        return AuthCourseResponseDto.builder()
                .count(content.size())
                .content(content)
                .status(StatusResponseDto.from(obj))
                .build();
    }
}

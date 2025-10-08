package com.smunity.dto;

import lombok.Builder;
import org.json.JSONArray;

import java.util.List;

@Builder
public record AuthCourseResponseDto(
        int count,
        List<CourseResponseDto> content
) {

    public static AuthCourseResponseDto from(JSONArray objs) {
        List<CourseResponseDto> content = CourseResponseDto.from(objs);
        return AuthCourseResponseDto.builder()
                .count(content.size())
                .content(content)
                .build();
    }
}

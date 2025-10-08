package com.smunity.dto;

import lombok.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.IntStream;

@Builder
public record CourseResponseDto(
        String number,
        String name,
        String type,
        String grade,
        String year,
        String semester,
        String domain,
        int credit
) {

    public static List<CourseResponseDto> from(JSONArray objs) {
        return IntStream.range(0, objs.length())
                .mapToObj(i -> from(objs.getJSONObject(i)))
                .toList();
    }

    private static CourseResponseDto from(JSONObject obj) {
        String type = obj.getString("CMP_DIV_NM");
        return CourseResponseDto.builder()
                .number(obj.optString("SBJ_NO", type))
                .name(obj.getString("SBJ_NM"))
                .type(type)
                .grade(obj.optString("GRD_NM"))
                .year(obj.getString("SCH_YEAR"))
                .semester(obj.optString("SMT_NM"))
                .domain(obj.optString("CULT_ARA_NM"))
                .credit(obj.getInt("CDT"))
                .build();
    }
}

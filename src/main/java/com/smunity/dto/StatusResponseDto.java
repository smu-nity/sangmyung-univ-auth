package com.smunity.dto;

import lombok.Builder;
import org.json.JSONObject;

@Builder
public record StatusResponseDto(
        int total,
        int completed,
        int required,
        int completion
) {

    public static StatusResponseDto from(JSONObject obj) {
        return of(obj.getJSONArray("dsCmpDivScrList").getJSONObject(0));
    }

    private static StatusResponseDto of(JSONObject obj) {
        return of(obj.getInt("GRDT_CRIT_CDT"), obj.getInt("TOTAL_GET_CDT"));
    }

    private static StatusResponseDto of(int total, int completed) {
        return StatusResponseDto.builder()
                .total(total)
                .completed(completed)
                .required(calculateRequired(total, completed))
                .completion(calculateCompletion(total, completed))
                .build();
    }

    private static int calculateRequired(int total, int completed) {
        return Math.max(0, total - completed);
    }

    private static int calculateCompletion(int total, int completed) {
        return total != 0 ? Math.min(100, completed * 100 / total) : 100;
    }
}

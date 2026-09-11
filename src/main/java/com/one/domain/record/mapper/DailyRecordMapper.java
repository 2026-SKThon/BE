package com.one.domain.record.mapper;

import com.one.domain.record.dto.response.DailyRecordResponse;
import com.one.domain.record.entity.DailyRecord;

public class DailyRecordMapper {

    public static DailyRecordResponse toDailyRecordResponse(DailyRecord record) {
        String title = switch (record.getRecordType()) {
            case TEMPERATURE -> record.getTemperature() + "°C 측정";
            case MEDICATION -> "복약 기록";
            case CONDITION -> "상태 체크";
        };

        String subtitle = switch (record.getRecordType()) {
            case TEMPERATURE -> "기기에서 자동으로 기록했어요";
            case MEDICATION -> record.getMedicationName() + " · " + record.getDosage() + "mL";
            case CONDITION -> null;
        };

        return DailyRecordResponse.builder()
                .recordedAt(record.getRecordedAt())
                .recordType(record.getRecordType())
                .title(title)
                .subtitle(subtitle)
                .build();
    }
}

package com.one.domain.record.mapper;

import com.one.domain.record.dto.response.DailyRecordCursorResponse;
import com.one.domain.record.dto.response.DailyRecordResponse;
import com.one.domain.record.entity.DailyRecord;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

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
                .id(record.getId())
                .recordedAt(record.getRecordedAt())
                .recordType(record.getRecordType())
                .title(title)
                .subtitle(subtitle)
                .build();
    }

    public static DailyRecordCursorResponse toDailyRecordCursorResponse(Slice<DailyRecord> slice) {
        List<DailyRecord> content = slice.getContent();
        boolean hasNext = slice.hasNext();

        LocalDateTime nextCursorCreatedAt = null;
        Long nextCursorId = null;

        if (hasNext && !content.isEmpty()) {
            DailyRecord last = content.get(content.size() - 1);
            nextCursorCreatedAt = last.getCreatedAt();
            nextCursorId = last.getId();
        }

        return DailyRecordCursorResponse.builder()
                .dailyRecords(content.stream().map(DailyRecordMapper::toDailyRecordResponse).toList())
                .nextCursorCreatedAt(nextCursorCreatedAt)
                .nextCursorId(nextCursorId)
                .hasNext(hasNext)
                .build();
    }
}

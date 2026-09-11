package com.one.domain.record.mapper;

import com.one.domain.record.dto.response.DailyRecordCursorResponse;
import com.one.domain.record.dto.response.DailyRecordResponse;
import com.one.domain.record.entity.DailyRecord;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public class DailyRecordMapper {

    public static DailyRecordResponse toDailyRecordResponse(DailyRecord record) {
        return DailyRecordResponse.builder()
                .id(record.getId())
                .recordedAt(record.getRecordedAt())
                .recordType(record.getRecordType())
                .temperature(record.getTemperature())
                .medicationName(record.getMedicationName())
                .dosage(record.getDosage())
                .responseStatus(record.getResponseStatus())
                .breathingStatus(record.getBreathingStatus())
                .hydrationStatus(record.getHydrationStatus())
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

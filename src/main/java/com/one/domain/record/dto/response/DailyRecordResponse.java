package com.one.domain.record.dto.response;

import com.one.domain.condition.enums.BreathingStatus;
import com.one.domain.condition.enums.HydrationStatus;
import com.one.domain.condition.enums.ResponseStatus;
import com.one.domain.record.enums.RecordType;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DailyRecordResponse {

    private Long id;
    private LocalDateTime recordedAt;
    private RecordType recordType;

    // TEMPERATURE
    private Double temperature;

    // MEDICATION
    private String medicationName;
    private Double dosage;

    // CONDITION
    private ResponseStatus responseStatus;
    private BreathingStatus breathingStatus;
    private HydrationStatus hydrationStatus;
}

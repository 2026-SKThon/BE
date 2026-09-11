package com.one.domain.child.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MedicationRecordSummary {

    private Long count;
    private LocalDateTime lastRecordedAt;
    private String medicationName;
    private Double dosage;
}

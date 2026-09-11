package com.one.domain.child.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecordSummaryResponse {

    private TemperatureRecordSummary temperature;
    private MedicationRecordSummary medication;
    private ConditionRecordSummary condition;
}

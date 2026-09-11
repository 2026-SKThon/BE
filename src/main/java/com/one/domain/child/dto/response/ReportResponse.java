package com.one.domain.child.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {

    private TemperatureFlowResponse temperatureFlow;
    private RecordSummaryResponse recordSummary;
}

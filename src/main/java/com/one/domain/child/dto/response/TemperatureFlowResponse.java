package com.one.domain.child.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TemperatureFlowResponse {

    private Double latestTemperature;
    private LocalDateTime latestMeasuredAt;
    private Double maxTemperature;
    private LocalDateTime maxMeasuredAt;
}

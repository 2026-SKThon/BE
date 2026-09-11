package com.one.domain.temperature.dto.request;

import com.one.domain.temperature.enums.MeasurementSite;
import com.one.domain.temperature.enums.TemperatureSource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "TemperatureCreateRequest: 여행 생성 요청 DTO")
public class TemperatureCreateRequest {

    private Long deviceId;

    private double temperature;

    private TemperatureSource temperatureSource;

    private MeasurementSite measurementSite;

    private LocalDateTime measuredAt;

    private String note;

}

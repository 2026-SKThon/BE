package com.one.domain.temperature.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "TemperaturePointResponse: 특정 시점 체온 DTO")
public class TemperaturePointResponse {
    private LocalDateTime measuredAt;  // 측정 시간
    private Double temperature;        // 체온 값
}

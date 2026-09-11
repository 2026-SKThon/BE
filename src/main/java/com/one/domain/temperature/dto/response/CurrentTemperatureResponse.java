package com.one.domain.temperature.dto.response;

import com.one.domain.fever.enums.FeverSeverity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "CurrentTemperatureResponse: 현재 체온 정보 응답 DTO")
public class CurrentTemperatureResponse {
    // 1. 핵심 측정 데이터
    private double currentTemperature;     // 현재 체온 (예: 37.7)
    private FeverSeverity severity;             // 상태 구분 (NORMAL, CAUTION, EMERGENCY) -> 캐릭터/색상 제어용

    // 2. 상태 메세지 & 변동 정보
    private Double temperatureDifference;  // 온도 변화량 (예: 0.4 또는 -0.4, 프론트에서 화살표 표시용)

    // 3. 측정 수신 정보
    private LocalDateTime lastMeasuredAt;  // 마지막 측정 시각

}

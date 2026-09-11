package com.one.domain.temperature.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(title = "TemperatureHistoryResponse: 체온 기록 리스트 조회 DTO")
public class TemperatureHistoryResponse {
    // 상단 요약 정보
    Double currentTemperature; // 현재 체온
    Double maxTemperature;     // 최고 체온
    Double minTemperature;     // 최저 체온

    // 그래프 포인트 리스트
    List<TemperaturePointResponse> points;
}

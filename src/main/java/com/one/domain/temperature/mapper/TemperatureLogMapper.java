package com.one.domain.temperature.mapper;

import com.one.domain.fever.enums.FeverSeverity;
import com.one.domain.temperature.dto.response.CurrentTemperatureResponse;
import com.one.domain.temperature.dto.response.TemperaturePointResponse;
import com.one.domain.temperature.entity.TemperatureLog;

public class TemperatureLogMapper {

    public static CurrentTemperatureResponse toTemperatureResponse(TemperatureLog temperatureLog, double temperatureDifference) {

        // Null Safe 처리: feverEpisode가 null이면 severity를 null로 안전하게 할당
        FeverSeverity severity = (temperatureLog.getFeverEpisode() != null)
                ? temperatureLog.getFeverEpisode().getSeverity()
                : null;

        return CurrentTemperatureResponse.builder()
                .currentTemperature(temperatureLog.getTemperature())
                .severity(severity) // ✨ 지점 수정: 메서드 직접 호출 대신 안전한 변수 전달
                .temperatureDifference(temperatureDifference)
                .lastMeasuredAt(temperatureLog.getMeasuredAt())
                .build();
    }

    public static TemperaturePointResponse toTemperaturePointResponse(TemperatureLog temperatureLog) {

        return TemperaturePointResponse.builder()
                .measuredAt(temperatureLog.getMeasuredAt())
                .temperature(temperatureLog.getTemperature())
                .build();

    }
}
package com.one.domain.temperature.controller;

import com.one.domain.temperature.dto.request.TemperatureCreateRequest;
import com.one.domain.temperature.dto.response.CurrentTemperatureResponse;
import com.one.domain.temperature.dto.response.TemperatureHistoryResponse;
import com.one.domain.temperature.service.TemperatureLogService;
import com.one.global.resolver.CurrentUserProvider;
import com.one.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "TemperatureLog", description = "체온 관련 API")
public class TemperatureLogController {

    private final TemperatureLogService temperatureLogService;
    private final CurrentUserProvider currentUserProvider;

    @Operation(summary = "최신 체온 조회 API", description = "가장 최근에 측정된 체온을 조회하는 API")
    @GetMapping("/children/{childId}/temperatures/latest")
    public ResponseEntity<BaseResponse<CurrentTemperatureResponse>> getLatestTemperature(@PathVariable Long childId) {

        CurrentTemperatureResponse response = temperatureLogService.getLatestCurrentTemperature(childId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "체온 그래프 조회 API", description = "그래프를 위한 체온 값들을 조회하는 API")
    @GetMapping("/children/{childId}/temperatures/history")
    public ResponseEntity<BaseResponse<TemperatureHistoryResponse>> getTemperatureHistory(
            @PathVariable Long childId) {

        TemperatureHistoryResponse response = temperatureLogService.getTemperatureHistory(childId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "체온 측정 API", description = "체온 측정 기록하는 API")
    @PostMapping("/children/{childId}/temperatures")
    public ResponseEntity<BaseResponse<CurrentTemperatureResponse>> recordTemperature(
            @RequestBody TemperatureCreateRequest request,
            @PathVariable Long childId) {

        CurrentTemperatureResponse response = temperatureLogService.createTemperatureLog(request, childId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(response));
    }
}

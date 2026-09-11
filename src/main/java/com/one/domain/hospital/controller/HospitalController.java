package com.one.domain.hospital.controller;

import com.one.domain.hospital.dto.response.HospitalDetailResponse;
import com.one.domain.hospital.dto.response.HospitalListResponse;
import com.one.domain.hospital.service.HospitalService;
import com.one.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hospitals")
@Tag(name = "Hospital", description = "병원 관련 API")
public class HospitalController {

    private final HospitalService hospitalService;

    @Operation(summary = "병원 목록 조회 API", description = "주변 병원 목록을 위/경도, 반경, 분류, 응급실 여부, 운영 여부 조건으로 조회하는 API")
    @GetMapping
    public ResponseEntity<BaseResponse<HospitalListResponse>> getHospitalList(
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double radiusKm,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean hasEmergencyRoom,
            @RequestParam(required = false) Boolean openNow
    ) {
        HospitalListResponse response = hospitalService.getHospitalList(
                latitude, longitude, radiusKm, category, hasEmergencyRoom, openNow
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "병원 상세 조회 API", description = "병원 ID로 상세 정보와 요일별 진료시간을 조회하는 API")
    @GetMapping("/{hospitalId}")
    public ResponseEntity<BaseResponse<HospitalDetailResponse>> getHospitalDetail(
            @PathVariable Long hospitalId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        HospitalDetailResponse response = hospitalService.getHospitalDetail(hospitalId, latitude, longitude);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "병원 검색 API", description = "병원명 또는 주소 키워드로 병원을 검색하는 API")
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<HospitalListResponse>> searchHospitals(
            @RequestParam String keyword,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        HospitalListResponse response = hospitalService.searchHospitals(keyword, latitude, longitude);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }
}

package com.one.domain.hospital.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "HospitalSummaryResponse: 병원 목록/검색 항목 응답 DTO")
public class HospitalSummaryResponse {
    Long id;
    String name;
    String category;
    Boolean hasEmergencyRoom;
    String phone;
    String address;

    Double latitude;  // 지도 마커 표시용 위도
    Double longitude; // 지도 마커 표시용 경도

    boolean openNow;      // 현재 운영 중 여부
    Double distanceKm;    // 사용자 위치 기준 거리(km), 위/경도 미전달 시 null
}

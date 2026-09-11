package com.one.domain.hospital.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(title = "HospitalDetailResponse: 병원 상세 조회 응답 DTO")
public class HospitalDetailResponse {
    Long id;
    String name;
    String category;
    Boolean hasEmergencyRoom;
    String phone;
    String emergencyPhone;
    String address;

    Double latitude;
    Double longitude;

    String note;
    String description;
    String directions;

    boolean openNow;   // 현재 운영 중 여부
    Double distanceKm; // 사용자 위치 기준 거리(km), 위/경도 미전달 시 null

    List<HospitalScheduleResponse> schedules; // 요일별 진료시간
}

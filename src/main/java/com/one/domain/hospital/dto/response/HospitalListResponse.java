package com.one.domain.hospital.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(title = "HospitalListResponse: 병원 목록 조회 응답 DTO")
public class HospitalListResponse {
    int totalCount; // 조회된 병원 수 (필터/반경 적용 후)

    List<HospitalSummaryResponse> hospitals;
}

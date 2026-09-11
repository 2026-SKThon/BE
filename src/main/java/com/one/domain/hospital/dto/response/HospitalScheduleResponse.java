package com.one.domain.hospital.dto.response;

import com.one.domain.hospital.enums.DayOfWeekType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "HospitalScheduleResponse: 요일별 진료시간 응답 DTO")
public class HospitalScheduleResponse {
    DayOfWeekType dayOfWeek; // 요일

    String startTime; // 진료 시작 시간 (HHmm)
    String endTime;   // 진료 종료 시간 (HHmm)
}

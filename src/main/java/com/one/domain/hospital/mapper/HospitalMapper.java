package com.one.domain.hospital.mapper;

import com.one.domain.hospital.dto.response.HospitalDetailResponse;
import com.one.domain.hospital.dto.response.HospitalScheduleResponse;
import com.one.domain.hospital.dto.response.HospitalSummaryResponse;
import com.one.domain.hospital.entity.Hospital;
import com.one.domain.hospital.entity.HospitalSchedule;

import java.util.Comparator;
import java.util.List;

public class HospitalMapper {

    public static HospitalSummaryResponse toSummaryResponse(Hospital hospital, boolean openNow, Double distanceKm) {
        return HospitalSummaryResponse.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .category(hospital.getCategory())
                .hasEmergencyRoom(hospital.getHasEmergencyRoom())
                .phone(hospital.getPhone())
                .address(hospital.getAddress())
                .latitude(hospital.getLatitude())
                .longitude(hospital.getLongitude())
                .openNow(openNow)
                .distanceKm(distanceKm)
                .build();
    }

    public static HospitalDetailResponse toDetailResponse(Hospital hospital, boolean openNow, Double distanceKm) {
        List<HospitalScheduleResponse> schedules = hospital.getSchedules().stream()
                .sorted(Comparator.comparing(HospitalSchedule::getDayOfWeek))
                .map(HospitalMapper::toScheduleResponse)
                .toList();

        return HospitalDetailResponse.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .category(hospital.getCategory())
                .hasEmergencyRoom(hospital.getHasEmergencyRoom())
                .phone(hospital.getPhone())
                .emergencyPhone(hospital.getEmergencyPhone())
                .address(hospital.getAddress())
                .latitude(hospital.getLatitude())
                .longitude(hospital.getLongitude())
                .note(hospital.getNote())
                .description(hospital.getDescription())
                .directions(hospital.getDirections())
                .openNow(openNow)
                .distanceKm(distanceKm)
                .schedules(schedules)
                .build();
    }

    private static HospitalScheduleResponse toScheduleResponse(HospitalSchedule schedule) {
        return HospitalScheduleResponse.builder()
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }
}

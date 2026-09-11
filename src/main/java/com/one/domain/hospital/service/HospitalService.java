package com.one.domain.hospital.service;

import com.one.domain.hospital.dto.response.HospitalDetailResponse;
import com.one.domain.hospital.dto.response.HospitalListResponse;
import com.one.domain.hospital.dto.response.HospitalSummaryResponse;
import com.one.domain.hospital.entity.Hospital;
import com.one.domain.hospital.entity.HospitalSchedule;
import com.one.domain.hospital.enums.DayOfWeekType;
import com.one.domain.hospital.exception.HospitalErrorCode;
import com.one.domain.hospital.mapper.HospitalMapper;
import com.one.domain.hospital.repository.HospitalRepository;
import com.one.domain.hospital.repository.HospitalSearchCondition;
import com.one.domain.hospital.util.GeoUtils;
import com.one.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalListResponse getHospitalList(
            Double latitude, Double longitude, Double radiusKm,
            String category, Boolean hasEmergencyRoom, Boolean openNow
    ) {
        log.info("[HospitalService] 병원 목록 조회 시작");

        HospitalSearchCondition condition = new HospitalSearchCondition(null, category, hasEmergencyRoom);
        List<Hospital> hospitals = hospitalRepository.search(condition);

        List<HospitalSummaryResponse> responses = toFilteredSortedSummaries(
                hospitals, latitude, longitude, radiusKm, openNow
        );

        return HospitalListResponse.builder()
                .totalCount(responses.size())
                .hospitals(responses)
                .build();
    }

    public HospitalListResponse searchHospitals(String keyword, Double latitude, Double longitude) {
        log.info("[HospitalService] 병원 검색 시작. keyword={}", keyword);

        HospitalSearchCondition condition = new HospitalSearchCondition(keyword, null, null);
        List<Hospital> hospitals = hospitalRepository.search(condition);

        List<HospitalSummaryResponse> responses = toFilteredSortedSummaries(
                hospitals, latitude, longitude, null, null
        );

        return HospitalListResponse.builder()
                .totalCount(responses.size())
                .hospitals(responses)
                .build();
    }

    public HospitalDetailResponse getHospitalDetail(Long hospitalId, Double latitude, Double longitude) {
        log.info("[HospitalService] 병원 상세 조회 시작. hospitalId={}", hospitalId);

        Hospital hospital = hospitalRepository.findByIdWithSchedules(hospitalId).orElseThrow(() -> {
            log.warn("[HospitalService] 존재하지 않는 병원입니다. hospitalId={}", hospitalId);
            return new CustomException(HospitalErrorCode.HOSPITAL_NOT_FOUND);
        });

        boolean openNow = isOpenNow(hospital.getSchedules());
        Double distanceKm = calculateDistance(latitude, longitude, hospital);

        return HospitalMapper.toDetailResponse(hospital, openNow, distanceKm);
    }

    private List<HospitalSummaryResponse> toFilteredSortedSummaries(
            List<Hospital> hospitals, Double latitude, Double longitude, Double radiusKm, Boolean openNow
    ) {
        boolean hasLocation = latitude != null && longitude != null;

        return hospitals.stream()
                .map(hospital -> HospitalMapper.toSummaryResponse(
                        hospital,
                        isOpenNow(hospital.getSchedules()),
                        calculateDistance(latitude, longitude, hospital)
                ))
                .filter(summary -> openNow == null || openNow == summary.isOpenNow())
                .filter(summary -> !hasLocation || radiusKm == null
                        || (summary.getDistanceKm() != null && summary.getDistanceKm() <= radiusKm))
                .sorted(hasLocation
                        ? Comparator.comparing(HospitalSummaryResponse::getDistanceKm,
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        : Comparator.comparing(HospitalSummaryResponse::getName))
                .toList();
    }

    private Double calculateDistance(Double latitude, Double longitude, Hospital hospital) {
        if (latitude == null || longitude == null
                || hospital.getLatitude() == null || hospital.getLongitude() == null) {
            return null;
        }
        return GeoUtils.distanceKm(latitude, longitude, hospital.getLatitude(), hospital.getLongitude());
    }

    private boolean isOpenNow(List<HospitalSchedule> schedules) {
        DayOfWeekType today = DayOfWeekType.from(LocalDateTime.now().getDayOfWeek());
        LocalTime now = LocalTime.now();
        int currentTime = now.getHour() * 100 + now.getMinute();

        return schedules.stream()
                .filter(schedule -> schedule.getDayOfWeek() == today)
                .anyMatch(schedule -> isWithinSchedule(schedule, currentTime));
    }

    private boolean isWithinSchedule(HospitalSchedule schedule, int currentTime) {
        if (schedule.getStartTime() == null || schedule.getEndTime() == null) {
            return false;
        }
        int start = Integer.parseInt(schedule.getStartTime());
        int end = Integer.parseInt(schedule.getEndTime());

        if (start <= end) {
            return currentTime >= start && currentTime <= end;
        }
        return currentTime >= start || currentTime <= end; // 자정을 넘기는 진료시간
    }
}

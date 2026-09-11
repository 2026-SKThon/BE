package com.one.domain.child.service;

import com.one.domain.child.dto.response.ReportResponse;
import com.one.domain.child.exception.ChildErrorCode;
import com.one.domain.child.mapper.ReportMapper;
import com.one.domain.child.repository.ChildRepository;
import com.one.domain.record.entity.DailyRecord;
import com.one.domain.record.enums.RecordType;
import com.one.domain.record.repository.DailyRecordRepository;
import com.one.domain.temperature.entity.TemperatureLog;
import com.one.domain.temperature.repository.TemperatureLogRepository;
import com.one.domain.user.repository.GuardianChildRepository;
import com.one.domain.user.repository.UserRepository;
import com.one.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChildService {

    public final TemperatureLogRepository temperatureLogRepository;
    public final GuardianChildRepository guardianChildRepository;
    public final ChildRepository childRepository;
    public final UserRepository userRepository;
    private final DailyRecordRepository dailyRecordRepository;

    public ReportResponse getReport(Long userId, Long childId) {

        log.info("[리포트 조회] START, childId = {}", childId);

        if (!guardianChildRepository.existsByUserIdAndChildId(userId, childId)) {
            log.warn("[리포트 조회] 내 아이의 리포트만 확인 가능합니다. userId = {}, childId = {}", userId, childId);
            throw new CustomException(ChildErrorCode.CHILD_NOT_MANAGED);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.toLocalDate().atStartOfDay();

        TemperatureLog latestTemLog = temperatureLogRepository
                .findLatestToday(childId, start, now)
                .orElse(null);

        TemperatureLog maxTempLog = temperatureLogRepository
                .findMaxToday(childId, start, now)
                .orElse(null);

        Map<RecordType, Long> countMap = dailyRecordRepository
                .countByRecordType(childId, start, now);

        Map<RecordType, DailyRecord> latestMap = dailyRecordRepository
                .findLatestPerRecordType(childId, start, now);

        log.info("[리포트 조회] END");

        return ReportMapper.toReportResponse(latestTemLog, maxTempLog, countMap, latestMap);
    }
}

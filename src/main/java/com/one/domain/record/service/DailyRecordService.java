package com.one.domain.record.service;

import com.one.domain.child.exception.ChildErrorCode;
import com.one.domain.record.dto.response.DailyRecordResponse;
import com.one.domain.record.entity.DailyRecord;
import com.one.domain.record.exception.RecordErrorCode;
import com.one.domain.record.mapper.DailyRecordMapper;
import com.one.domain.record.repository.DailyRecordRepository;
import com.one.domain.user.repository.GuardianChildRepository;
import com.one.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyRecordService {

    private final DailyRecordRepository dailyRecordRepository;
    private final GuardianChildRepository guardianChildRepository;

    // 홈화면 오늘의 기록 최근 3개
    public List<DailyRecordResponse> getDailyRecordsTop3(Long currentUserId, Long childId) {


        log.info("[데일리 기록 최근 3개 조회] START, childId = {}", childId);

        if(!guardianChildRepository.existsByUserIdAndChildId(currentUserId, childId)) {
            log.info("[데일리 기록 최근 3개 조회] 내 아이의 기록만 확인 가능합니다. currentUserId = {}. childId = {}", currentUserId, childId);
            throw new CustomException(ChildErrorCode.CHILD_NOT_MANAGED);
        }

        // 오늘 날짜 00시
        LocalDateTime start = LocalDate.now().atStartOfDay();

        //다음날 00시
        LocalDateTime end = start.plusDays(1);

        PageRequest request = PageRequest.of(0, 3);

        List<DailyRecord> top3ByChildIdToday = dailyRecordRepository
                .findTop3ByChildIdToday(childId, start, end, request);

        log.info("[데일리 기록 최근 3개 조회] END");

        return top3ByChildIdToday.stream()
                .map(DailyRecordMapper::toDailyRecordResponse)
                .toList();
    }

}

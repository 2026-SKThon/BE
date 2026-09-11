package com.one.domain.condition.service;

import com.one.domain.child.entity.Child;
import com.one.domain.child.exception.ChildErrorCode;
import com.one.domain.child.repository.ChildRepository;
import com.one.domain.condition.dto.request.ConditionCheckRequest;
import com.one.domain.condition.entity.ConditionCheck;
import com.one.domain.condition.repository.ConditionCheckRepository;
import com.one.domain.record.entity.DailyRecord;
import com.one.domain.record.repository.DailyRecordRepository;
import com.one.domain.user.entity.User;
import com.one.domain.user.exception.UserErrorCode;
import com.one.domain.user.repository.GuardianChildRepository;
import com.one.domain.user.repository.UserRepository;
import com.one.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConditionService {

    private final GuardianChildRepository guardianChildRepository;
    private final ConditionCheckRepository conditionCheckRepository;
    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final DailyRecordRepository dailyRecordRepository;

    @Transactional
    public void setConditionReport(Long currentUserId, Long childId, ConditionCheckRequest request) {

        log.info("[아이 상태 기록] START");

        // 부모와 아이가 연결 됐는지 확인
        if (!guardianChildRepository.existsByUserIdAndChildId(currentUserId, childId)) {
            log.warn("[아이 상태 기록] 내 아이의 상태만 기록 가능합니다. currentUserId = {}, childId = {}", currentUserId, childId);
            throw new CustomException(ChildErrorCode.CHILD_NOT_MANAGED);
        }

        // 아이 조회
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> {
                    log.warn("[아이 상태 기록] 아이를 찾을 수 없습니다. childId = {}", childId);
                    return new CustomException(ChildErrorCode.CHILD_NOT_FOUND);
                });

        // 유저 조회
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> {
                    log.warn("[아이 상태 기록] 유저를 찾을 수 없습니다. userId = {}", currentUserId);
                    return new CustomException(UserErrorCode.USER_NOT_FOUND);
                });

        // 상태 저장
        ConditionCheck conditionCheck = ConditionCheck.create(
                child, user, request.getResponseStatus(), request.getBreathingStatus(), request.getHydrationStatus(), request.getNote()
        );
        conditionCheckRepository.save(conditionCheck);

        // 데일리 레코드 저장
        DailyRecord dailyRecord = DailyRecord.createCondition(
                child, LocalDateTime.now(),
                request.getResponseStatus(), request.getBreathingStatus(), request.getHydrationStatus()
        );
        dailyRecordRepository.save(dailyRecord);

        log.info("[아이 상태 기록] END");
    }
}

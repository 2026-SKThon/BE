package com.one.domain.record.repository.custom;

import com.one.domain.record.entity.DailyRecord;
import com.one.domain.record.enums.RecordType;

import java.time.LocalDateTime;
import java.util.Map;

public interface DailyRecordCustomRepository {

    // 데일리 레코드 type별 몇개인지
    Map<RecordType, Long> countByRecordType(
            Long childId, LocalDateTime start, LocalDateTime end
    );

    // 데일리 레코드 타입별 가장 최근 레코드 조회
    Map<RecordType, DailyRecord> findLatestPerRecordType(
            Long childId, LocalDateTime start, LocalDateTime end
    );
}

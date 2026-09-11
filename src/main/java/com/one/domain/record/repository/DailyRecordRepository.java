package com.one.domain.record.repository;

import com.one.domain.record.entity.DailyRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {

    @Query("""
            select r from DailyRecord r
            where r.child.id = :childId
              and r.recordedAt >= :start and r.recordedAt < :end
            order by r.recordedAt desc
            """)
    List<DailyRecord> findTop3ByChildIdToday(
            @Param("childId") Long childId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("""
            select r from DailyRecord r
            where r.child.id = :childId
              and r.recordedAt >= :start and r.recordedAt < :end
              and (:cursorCreatedAt is null
                   or r.createdAt < :cursorCreatedAt
                   or (r.createdAt = :cursorCreatedAt and r.id < :cursorId))
            order by r.createdAt desc, r.id desc
            """)
    Slice<DailyRecord> findAllByChildIdTodayWithCursor(
            @Param("childId") Long childId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}

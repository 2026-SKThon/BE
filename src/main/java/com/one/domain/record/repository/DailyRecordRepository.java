package com.one.domain.record.repository;

import com.one.domain.record.entity.DailyRecord;
import org.springframework.data.domain.Pageable;
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
}

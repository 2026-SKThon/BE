package com.one.domain.temperature.repository;

import com.one.domain.child.entity.Child;
import com.one.domain.temperature.entity.TemperatureLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TemperatureLogRepository extends JpaRepository<TemperatureLog, Long> {

    Optional<TemperatureLog> findTopByChildOrderByMeasuredAtDesc(Child child);

    List<TemperatureLog> findByChildAndMeasuredAtGreaterThanEqualOrderByMeasuredAtAsc(
            Child child,
            LocalDateTime basisTime
    );

    // 오늘 가장 최신 체온 로그
    @Query("""
        select tem from TemperatureLog tem
        where tem.child.id = :childId
          and tem.measuredAt >= :start and tem.measuredAt < :end
        order by tem.measuredAt desc
        limit 1
    """)
    Optional<TemperatureLog> findLatestToday(
            @Param("childId") Long childId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // 오늘 최고 체온 로그
    @Query("""
        select tem from TemperatureLog tem
        where tem.child.id = :childId
          and tem.measuredAt >= :start and tem.measuredAt < :end
          and tem.temperature = (
              select max(t.temperature) from TemperatureLog t
              where t.child.id = :childId
                and t.measuredAt >= :start and t.measuredAt < :end
          )
        order by tem.measuredAt asc
        limit 1
    """)
    Optional<TemperatureLog> findMaxToday(
            @Param("childId") Long childId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}

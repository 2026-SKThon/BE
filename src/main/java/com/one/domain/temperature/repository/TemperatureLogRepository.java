package com.one.domain.temperature.repository;

import com.one.domain.child.entity.Child;
import com.one.domain.temperature.entity.TemperatureLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TemperatureLogRepository extends JpaRepository<TemperatureLog, Long> {

    Optional<TemperatureLog> findTopByChildOrderByMeasuredAtDesc(Child child);

    List<TemperatureLog> findByChildAndMeasuredAtGreaterThanEqualOrderByMeasuredAtAsc(
            Child child,
            LocalDateTime basisTime
    );
}

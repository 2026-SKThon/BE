package com.one.domain.record.entity;

import com.one.domain.child.entity.Child;
import com.one.domain.condition.enums.BreathingStatus;
import com.one.domain.condition.enums.HydrationStatus;
import com.one.domain.condition.enums.ResponseStatus;
import com.one.domain.record.enums.RecordType;
import com.one.global.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "daily_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DailyRecord extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_type", nullable = false, length = 15)
    private RecordType recordType;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "medication_name", length = 100)
    private String medicationName;

    @Column(name = "dosage")
    private Double dosage;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_status", length = 10)
    private ResponseStatus responseStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "breathing_status", length = 10)
    private BreathingStatus breathingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "hydration_status", length = 10)
    private HydrationStatus hydrationStatus;


    public static DailyRecord createTemperature(Child child, LocalDateTime recordedAt, Double temperature) {
        return DailyRecord.builder()
                .child(child)
                .recordType(RecordType.TEMPERATURE)
                .recordedAt(recordedAt)
                .temperature(temperature)
                .build();
    }

    public static DailyRecord createMedication(Child child, LocalDateTime recordedAt, String medicationName, Double dosage) {
        return DailyRecord.builder()
                .child(child)
                .recordType(RecordType.MEDICATION)
                .recordedAt(recordedAt)
                .medicationName(medicationName)
                .dosage(dosage)
                .build();
    }

    // 상태기록 생성자
    public static DailyRecord createCondition(
            Child child, LocalDateTime recordedAt,
            ResponseStatus responseStatus,
            BreathingStatus breathingStatus,
            HydrationStatus hydrationStatus)
    {

        return DailyRecord.builder()
                .child(child)
                .recordType(RecordType.CONDITION)
                .recordedAt(recordedAt)
                .responseStatus(responseStatus)
                .breathingStatus(breathingStatus)
                .hydrationStatus(hydrationStatus)
                .build();
    }
}

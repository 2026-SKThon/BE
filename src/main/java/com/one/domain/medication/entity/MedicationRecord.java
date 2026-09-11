package com.one.domain.medication.entity;

import com.one.domain.child.entity.Child;
import com.one.domain.fever.entity.FeverEpisode;
import com.one.domain.medication.enums.MedicationType;
import com.one.domain.user.entity.User;
import com.one.global.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "medication_records")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicationRecord extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fever_episode_id")
    private FeverEpisode feverEpisode;

    @Column(name = "medication_name", nullable = false, length = 100)
    private String medicationName;

    @Enumerated(EnumType.STRING)
    @Column(name = "medication_type", nullable = false, length = 10)
    private MedicationType medicationType;

    @Column(nullable = false)
    private double dosage;

    @Column(name = "dosage_unit", nullable = false, length = 10)
    private String dosageUnit;

    @Column(name = "temperature_at_time")
    private Double temperatureAtTime;

    @Column(name = "taken_at", nullable = false)
    private LocalDateTime takenAt;

    @Column(columnDefinition = "TEXT")
    private String note;
}

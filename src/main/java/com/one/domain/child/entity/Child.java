package com.one.domain.child.entity;

import com.one.domain.child.enums.AllergyStatus;
import com.one.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "children")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Child extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column
    private Double weight;

    @Column(name = "weight_recorded_at")
    private LocalDate weightRecordedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "allergy_status", nullable = false, length = 10)
    private AllergyStatus allergyStatus;

    @Column(name = "allergy_detail", columnDefinition = "TEXT")
    private String allergyDetail;

    @Column(name = "regular_medication", columnDefinition = "TEXT")
    private String regularMedication;

    public void updateProfile(String name, LocalDate birthDate, Double weight, LocalDate weightRecordedAt,
                               AllergyStatus allergyStatus, String allergyDetail, String regularMedication) {
        if (name != null) {
            this.name = name;
        }
        if (birthDate != null) {
            this.birthDate = birthDate;
        }
        if (weight != null) {
            this.weight = weight;
            this.weightRecordedAt = weightRecordedAt != null ? weightRecordedAt : LocalDate.now();
        }
        if (allergyStatus != null) {
            this.allergyStatus = allergyStatus;
        }
        if (allergyDetail != null) {
            this.allergyDetail = allergyDetail;
        }
        if (regularMedication != null) {
            this.regularMedication = regularMedication;
        }
    }
}

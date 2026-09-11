package com.one.domain.hospital.entity;
import com.one.global.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "hospitals")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 30)
    private String category;

    @Column(name = "has_emergency_room", nullable = false)
    private Boolean hasEmergencyRoom;

    @Column(length = 30)
    private String phone;

    @Column(name = "emergency_phone", length = 30)
    private String emergencyPhone;

    @Column(length = 255)
    private String address;

    private Double latitude;

    private Double longitude;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String directions;

    @Builder.Default
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HospitalSchedule> schedules = new ArrayList<>();
}
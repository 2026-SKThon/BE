package com.one.domain.hospital.entity;
import com.one.domain.hospital.enums.DayOfWeekType;
import com.one.global.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@Table(name = "hospital_schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class HospitalSchedule extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 10)
    private DayOfWeekType dayOfWeek;

    @Column(name = "start_time", length = 4)
    private String startTime;

    @Column(name = "end_time", length = 4)
    private String endTime;
}

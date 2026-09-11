package com.one.domain.temperature.entity;

import com.one.domain.child.entity.Child;
import com.one.domain.device.entity.Device;
import com.one.domain.fever.entity.FeverEpisode;
import com.one.domain.temperature.enums.MeasurementSite;
import com.one.domain.temperature.enums.TemperatureSource;
import com.one.global.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "temperature_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TemperatureLog extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fever_episode_id")
    private FeverEpisode feverEpisode;

    @Column(nullable = false)
    private double temperature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TemperatureSource source;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_site", length = 10)
    private MeasurementSite measurementSite;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "measured_at", nullable = false)
    private LocalDateTime measuredAt;
}

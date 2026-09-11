package com.one.domain.fever.entity;

import com.one.domain.child.entity.Child;
import com.one.domain.fever.enums.FeverStatus;
import com.one.domain.fever.enums.FeverSeverity;
import com.one.global.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "fever_episodes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeverEpisode extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private FeverStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private FeverSeverity severity;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    /**
     * 발열 에피소드 종료
     */
    public void endEpisode(LocalDateTime endedAt) {
        this.status = FeverStatus.ENDED;
        this.endedAt = endedAt;
    }

    /**
     * 심각도 갱신 (유연한 변경)
     */
    public void updateSeverity(FeverSeverity newSeverity) {
        if (newSeverity != null && this.severity != newSeverity) {
            this.severity = newSeverity;
        }
    }
}

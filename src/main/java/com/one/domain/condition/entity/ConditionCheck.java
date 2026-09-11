package com.one.domain.condition.entity;

import com.one.domain.child.entity.Child;
import com.one.domain.condition.enums.BreathingStatus;
import com.one.domain.condition.enums.HydrationStatus;
import com.one.domain.condition.enums.ResponseStatus;
import com.one.domain.user.entity.User;
import com.one.global.entity.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "condition_checks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ConditionCheck extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_status", nullable = false, length = 10)
    private ResponseStatus responseStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "breathing_status", nullable = false, length = 10)
    private BreathingStatus breathingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "hydration_status", nullable = false, length = 10)
    private HydrationStatus hydrationStatus;

    @Column(columnDefinition = "TEXT")
    private String note;


    public static ConditionCheck create (Child child, User user, ResponseStatus responseStatus, BreathingStatus breathingStatus, HydrationStatus hydrationStatus, String note) {
        return ConditionCheck.builder()
                .child(child)
                .user(user)
                .responseStatus(responseStatus)
                .breathingStatus(breathingStatus)
                .hydrationStatus(hydrationStatus)
                .note(note)
                .build();
    }
}

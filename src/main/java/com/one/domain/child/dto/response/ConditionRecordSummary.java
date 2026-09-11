package com.one.domain.child.dto.response;

import com.one.domain.condition.enums.BreathingStatus;
import com.one.domain.condition.enums.HydrationStatus;
import com.one.domain.condition.enums.ResponseStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConditionRecordSummary {

    private Long count;
    private LocalDateTime lastRecordedAt;
    private ResponseStatus responseStatus;
    private BreathingStatus breathingStatus;
    private HydrationStatus hydrationStatus;
}

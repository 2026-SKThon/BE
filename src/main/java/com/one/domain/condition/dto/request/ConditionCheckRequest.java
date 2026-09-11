package com.one.domain.condition.dto.request;

import com.one.domain.condition.enums.BreathingStatus;
import com.one.domain.condition.enums.HydrationStatus;
import com.one.domain.condition.enums.ResponseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConditionCheckRequest {

    @NotNull(message = "반응 상태는 필수입니다.")
    private ResponseStatus responseStatus;

    @NotNull(message = "호흡 상태는 필수입니다.")
    private BreathingStatus breathingStatus;

    @NotNull(message = "수분 상태는 필수입니다.")
    private HydrationStatus hydrationStatus;

    private String note;
}
package com.one.domain.fever.enums;

public enum FeverSeverity {
    CAUTION, EMERGENCY;

    public static FeverSeverity from(double temperature) {
        if (temperature >= 38.5) return EMERGENCY;
        if (temperature >= 37.5) return CAUTION;
        return null; // 정상 체온
    }

    // 정상: 36.4 ~37.4
    // 주의: 37.5 ~ 38.4
    // 위험 38.5 이상
}

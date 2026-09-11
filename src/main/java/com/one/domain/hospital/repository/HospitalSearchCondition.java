package com.one.domain.hospital.repository;

public record HospitalSearchCondition(
        String keyword,
        String category,
        Boolean hasEmergencyRoom
) {
}

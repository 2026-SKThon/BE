package com.one.domain.hospital.repository;

import com.one.domain.hospital.entity.Hospital;

import java.util.List;

public interface HospitalQueryRepository {
    List<Hospital> search(HospitalSearchCondition condition);
}

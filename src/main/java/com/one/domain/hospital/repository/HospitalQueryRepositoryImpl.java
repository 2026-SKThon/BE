package com.one.domain.hospital.repository;

import com.one.domain.hospital.entity.Hospital;
import com.one.domain.hospital.entity.QHospital;
import com.one.domain.hospital.entity.QHospitalSchedule;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HospitalQueryRepositoryImpl implements HospitalQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Hospital> search(HospitalSearchCondition condition) {
        QHospital hospital = QHospital.hospital;
        QHospitalSchedule schedule = QHospitalSchedule.hospitalSchedule;

        return queryFactory
                .selectFrom(hospital)
                .leftJoin(hospital.schedules, schedule).fetchJoin()
                .where(
                        keywordContains(condition.keyword()),
                        categoryEq(condition.category()),
                        hasEmergencyRoomEq(condition.hasEmergencyRoom())
                )
                .distinct()
                .fetch();
    }

    private BooleanExpression keywordContains(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        QHospital hospital = QHospital.hospital;
        return hospital.name.containsIgnoreCase(keyword)
                .or(hospital.address.containsIgnoreCase(keyword));
    }

    private BooleanExpression categoryEq(String category) {
        return StringUtils.hasText(category) ? QHospital.hospital.category.eq(category) : null;
    }

    private BooleanExpression hasEmergencyRoomEq(Boolean hasEmergencyRoom) {
        return hasEmergencyRoom != null ? QHospital.hospital.hasEmergencyRoom.eq(hasEmergencyRoom) : null;
    }
}

package com.one.domain.record.repository.custom;

import com.one.domain.record.entity.DailyRecord;
import com.one.domain.record.enums.RecordType;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.one.domain.record.entity.QDailyRecord.dailyRecord;

@RequiredArgsConstructor
public class DailyRecordCustomRepositoryImpl implements DailyRecordCustomRepository {

    private final JPAQueryFactory queryFactory;


    // 데일리 레코드 type별 몇개인지
    @Override
    public Map<RecordType, Long> countByRecordType(Long childId, LocalDateTime start, LocalDateTime end) {
        List<Tuple> result = queryFactory
                .select(dailyRecord.recordType, dailyRecord.count())
                .from(dailyRecord)
                .where(
                        childEq(childId),
                        recordGoe(start),
                        recordLt(end)
                )
                .groupBy(dailyRecord.recordType)
                .fetch();

        Map<RecordType, Long> countMap = new EnumMap<>(RecordType.class);
        for (Tuple tuple : result) {
            countMap.put(tuple.get(dailyRecord.recordType), tuple.get(dailyRecord.count()));
        }
        return countMap;
    }


    // 데일리 레코드 타입별 가장 최근 레코드 조회
    @Override
    public Map<RecordType, DailyRecord> findLatestPerRecordType(Long childId, LocalDateTime start, LocalDateTime end) {
        Map<RecordType, DailyRecord> latestMap = new EnumMap<>(RecordType.class);

        for (RecordType type : RecordType.values()) {
            DailyRecord latest = queryFactory
                    .selectFrom(dailyRecord)
                    .where(
                            childEq(childId),
                            dailyRecord.recordType.eq(type),
                            recordGoe(start),
                            recordLt(end)
                    )
                    .orderBy(dailyRecord.recordedAt.desc())
                    .limit(1)
                    .fetchOne();

            if (latest != null) {
                latestMap.put(type, latest);
            }
        }
        return latestMap;
    }

    private BooleanExpression childEq(Long childId) {
        return childId != null ? dailyRecord.child.id.eq(childId) : null;
    }

    private BooleanExpression recordGoe(LocalDateTime start) {
        return start != null ? dailyRecord.recordedAt.goe(start) : null;
    }

    private BooleanExpression recordLt(LocalDateTime end) {
        return end != null ? dailyRecord.recordedAt.lt(end) : null;
    }
}

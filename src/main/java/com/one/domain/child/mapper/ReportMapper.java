package com.one.domain.child.mapper;

import com.one.domain.child.dto.response.*;
import com.one.domain.record.entity.DailyRecord;
import com.one.domain.record.enums.RecordType;
import com.one.domain.temperature.entity.TemperatureLog;

import java.util.Map;

public class ReportMapper {

    public static ReportResponse toReportResponse(
            TemperatureLog latestTempLog,
            TemperatureLog maxTempLog,
            Map<RecordType, Long> countMap,
            Map<RecordType, DailyRecord> latestMap
    ) {
        return ReportResponse.builder()
                .temperatureFlow(toTemperatureFlowResponse(latestTempLog, maxTempLog))
                .recordSummary(toRecordSummaryResponse(countMap, latestMap))
                .build();
    }

    private static TemperatureFlowResponse toTemperatureFlowResponse(
            TemperatureLog latestTempLog,
            TemperatureLog maxTempLog
    ) {
        if (latestTempLog == null && maxTempLog == null) {
            return null;
        }
        return TemperatureFlowResponse.builder()
                .latestTemperature(latestTempLog != null ? latestTempLog.getTemperature() : null)
                .latestMeasuredAt(latestTempLog != null ? latestTempLog.getMeasuredAt() : null)
                .maxTemperature(maxTempLog != null ? maxTempLog.getTemperature() : null)
                .maxMeasuredAt(maxTempLog != null ? maxTempLog.getMeasuredAt() : null)
                .build();
    }

    private static RecordSummaryResponse toRecordSummaryResponse(
            Map<RecordType, Long> countMap,
            Map<RecordType, DailyRecord> latestMap
    ) {
        return RecordSummaryResponse.builder()
                .temperature(toTemperatureRecordSummary(countMap, latestMap))
                .medication(toMedicationRecordSummary(countMap, latestMap))
                .condition(toConditionRecordSummary(countMap, latestMap))
                .build();
    }

    private static TemperatureRecordSummary toTemperatureRecordSummary(
            Map<RecordType, Long> countMap,
            Map<RecordType, DailyRecord> latestMap
    ) {
        DailyRecord latest = latestMap.get(RecordType.TEMPERATURE);
        if (latest == null) return null;
        return TemperatureRecordSummary.builder()
                .count(countMap.getOrDefault(RecordType.TEMPERATURE, 0L))
                .lastRecordedAt(latest.getRecordedAt())
                .lastTemperature(latest.getTemperature())
                .build();
    }

    private static MedicationRecordSummary toMedicationRecordSummary(
            Map<RecordType, Long> countMap,
            Map<RecordType, DailyRecord> latestMap
    ) {
        DailyRecord latest = latestMap.get(RecordType.MEDICATION);
        if (latest == null) return null;
        return MedicationRecordSummary.builder()
                .count(countMap.getOrDefault(RecordType.MEDICATION, 0L))
                .lastRecordedAt(latest.getRecordedAt())
                .medicationName(latest.getMedicationName())
                .dosage(latest.getDosage())
                .build();
    }

    private static ConditionRecordSummary toConditionRecordSummary(
            Map<RecordType, Long> countMap,
            Map<RecordType, DailyRecord> latestMap
    ) {
        DailyRecord latest = latestMap.get(RecordType.CONDITION);
        if (latest == null) return null;
        return ConditionRecordSummary.builder()
                .count(countMap.getOrDefault(RecordType.CONDITION, 0L))
                .lastRecordedAt(latest.getRecordedAt())
                .responseStatus(latest.getResponseStatus())
                .breathingStatus(latest.getBreathingStatus())
                .hydrationStatus(latest.getHydrationStatus())
                .build();
    }
}

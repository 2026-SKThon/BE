package com.one.domain.record.controller;

import com.one.domain.record.dto.response.DailyRecordCursorResponse;
import com.one.domain.record.dto.response.DailyRecordResponse;
import com.one.domain.record.service.DailyRecordService;
import com.one.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Daily Record", description = "오늘의 기록 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/children/{childId}/records")
public class DailyRecordController {

    private final DailyRecordService dailyRecordService;

    @Operation(summary = "오늘의 기록 최근 3개 조회", description = "홈화면에 표시할 오늘의 기록 최근 3개를 조회합니다.")
    @GetMapping("/top3")
    public ResponseEntity<BaseResponse<List<DailyRecordResponse>>> getDailyRecordsTop3(
            @Parameter(description = "유저 ID", required = true, example = "1") @RequestHeader("X-USER-ID") Long userId,
            @Parameter(description = "아이 ID") @PathVariable Long childId
    ) {
        List<DailyRecordResponse> dailyRecordsTop3 = dailyRecordService
                .getDailyRecordsTop3(userId, childId);

        return ResponseEntity.status(200)
                .body(BaseResponse.success(dailyRecordsTop3));
    }

    @Operation(summary = "오늘의 기록 전체 조회", description = "오늘의 기록 전체를 커서 기반 페이지네이션으로 조회합니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<DailyRecordCursorResponse>> getAllDailyRecords(
            @Parameter(description = "유저 ID", required = true, example = "1") @RequestHeader("X-USER-ID") Long userId,
            @Parameter(description = "아이 ID") @PathVariable Long childId,
            @Parameter(description = "페이지 크기", example = "20") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "커서 ID (이전 응답u의 nextCrsorId)") @RequestParam(required = false) Long cursorId,
            @Parameter(description = "커서 생성일시 (이전 응답의 nextCursorCreatedAt)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursor
    ) {
        DailyRecordCursorResponse response = dailyRecordService
                .getAllDailyRecords(userId, childId, size, cursorId, cursor);

        return ResponseEntity.status(200)
                .body(BaseResponse.success(response));
    }
}

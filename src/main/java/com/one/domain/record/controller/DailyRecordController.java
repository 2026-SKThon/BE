package com.one.domain.record.controller;

import com.one.domain.record.dto.response.DailyRecordResponse;
import com.one.domain.record.service.DailyRecordService;
import com.one.global.resolver.CurrentUserProvider;
import com.one.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/children/{childId}/records")
public class DailyRecordController {

    private final CurrentUserProvider currentUserProvider;
    private final DailyRecordService dailyRecordService;


    @GetMapping("/top3")
    public ResponseEntity<BaseResponse<List<DailyRecordResponse>>> getDailyRecordsTop3(
            @PathVariable Long childId
    ) {
        List<DailyRecordResponse> dailyRecordsTop3 = dailyRecordService
                .getDailyRecordsTop3(currentUserProvider.getCurrentUserId(),childId);

        return ResponseEntity.status(200)
                .body(BaseResponse.success(dailyRecordsTop3));
    }
}

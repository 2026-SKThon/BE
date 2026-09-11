package com.one.domain.child.controller;

import com.one.domain.child.dto.response.ReportResponse;
import com.one.domain.child.service.ChildService;
import com.one.global.resolver.CurrentUserProvider;
import com.one.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Child", description = "아이 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/children")
public class ChildController {

    private final ChildService childService;
    private final CurrentUserProvider currentUserProvider;

    @Operation(summary = "오늘의 리포트 조회")
    @GetMapping("/{childId}/report")
    public ResponseEntity<BaseResponse<ReportResponse>> getReport(
            @Parameter(description = "아이 ID") @PathVariable Long childId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                        .body(BaseResponse.success(childService.getReport(currentUserProvider.getCurrentUserId(), childId)));

    }
}

package com.one.domain.condition.controller;

import com.one.domain.condition.dto.request.ConditionCheckRequest;
import com.one.domain.condition.service.ConditionService;
import com.one.global.resolver.CurrentUserProvider;
import com.one.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Condition Check", description = "아이 상태 기록 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/children/{childId}/condition")
public class ConditionCheckController {

    private final ConditionService conditionService;
    private final CurrentUserProvider currentUserProvider;

    @Operation(summary = "아이 상태 기록")
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> setConditionReport(
            @PathVariable Long childId,
            @RequestBody @Valid ConditionCheckRequest request
    ) {

        conditionService.setConditionReport(currentUserProvider.getCurrentUserId(), childId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(null));
    }

}

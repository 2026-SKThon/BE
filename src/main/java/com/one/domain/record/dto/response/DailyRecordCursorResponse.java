package com.one.domain.record.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DailyRecordCursorResponse {

    private List<DailyRecordResponse> dailyRecords;
    private LocalDateTime nextCursorCreatedAt;
    private Long nextCursorId;
    private boolean hasNext;
}

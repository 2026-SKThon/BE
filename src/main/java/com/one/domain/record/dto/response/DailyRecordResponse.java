package com.one.domain.record.dto.response;

import com.one.domain.record.enums.RecordType;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DailyRecordResponse {

    private Long id;
    private LocalDateTime recordedAt;
    private RecordType recordType;
    private String title;
    private String subtitle;
}

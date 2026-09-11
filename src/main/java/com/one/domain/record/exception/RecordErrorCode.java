package com.one.domain.record.exception;

import com.one.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecordErrorCode implements ErrorCode {

    RECORD_NOT_FOUND("RECORD_404", "기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RECORD_ACCESS_DENIED("RECORD_403", "해당 기록에 접근할 수 없습니다.", HttpStatus.FORBIDDEN);


    private final String code;
    private final String message;
    private final HttpStatus status;
}

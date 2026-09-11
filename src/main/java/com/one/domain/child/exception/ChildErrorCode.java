package com.one.domain.child.exception;

import com.one.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChildErrorCode implements ErrorCode {

    CHILD_NOT_MANAGED("CHILD_403", "관리 중인 아이가 아닙니다.", HttpStatus.FORBIDDEN),
    CHILD_NOT_FOUND("CHILD_404", "아이를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

}

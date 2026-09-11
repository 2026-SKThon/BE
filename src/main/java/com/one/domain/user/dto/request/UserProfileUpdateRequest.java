package com.one.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "UserProfileUpdateRequest: 보호자 프로필 수정 요청 DTO (부분 수정, null 필드는 변경하지 않음)")
public class UserProfileUpdateRequest {

    @Schema(description = "보호자 이름", example = "민호 보호자")
    private String name;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
}

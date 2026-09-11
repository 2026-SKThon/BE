package com.one.domain.user.dto.response;

import com.one.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "UserProfileResponse: 보호자 프로필 응답 DTO")
public class UserProfileResponse {

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "보호자 이름", example = "민호 보호자")
    private String name;

    @Schema(description = "이메일", example = "guardian@example.com")
    private String email;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}

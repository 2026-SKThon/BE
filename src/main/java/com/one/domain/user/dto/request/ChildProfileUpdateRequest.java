package com.one.domain.user.dto.request;

import com.one.domain.child.enums.AllergyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "ChildProfileUpdateRequest: 아이 프로필 수정 요청 DTO (부분 수정, null 필드는 변경하지 않음)")
public class ChildProfileUpdateRequest {

    @Schema(description = "아이 이름", example = "민호")
    private String name;

    @Schema(description = "생년월일 (월령 계산에 사용)", example = "2025-10-02")
    private LocalDate birthDate;

    @Schema(description = "최근 체중(kg)", example = "9.2")
    private Double weight;

    @Schema(description = "체중 기록일 (미입력 시 서버에서 오늘 날짜로 저장)", example = "2026-09-10")
    private LocalDate weightRecordedAt;

    @Schema(description = "알레르기 여부 (NONE, YES, UNKNOWN)", example = "NONE")
    private AllergyStatus allergyStatus;

    @Schema(description = "알레르기 상세")
    private String allergyDetail;

    @Schema(description = "평소 복용하는 약")
    private String regularMedication;
}

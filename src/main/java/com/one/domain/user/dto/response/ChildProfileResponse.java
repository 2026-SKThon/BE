package com.one.domain.user.dto.response;

import com.one.domain.child.entity.Child;
import com.one.domain.child.enums.AllergyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.Period;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ChildProfileResponse: 아이 프로필 응답 DTO")
public class ChildProfileResponse {

    @Schema(description = "아이 ID", example = "1")
    private Long childId;

    @Schema(description = "아이 이름", example = "민호")
    private String name;

    @Schema(description = "생년월일", example = "2025-10-02")
    private LocalDate birthDate;

    @Schema(description = "월령(개월수) - 생년월일로 서버에서 계산", example = "11")
    private int ageInMonths;

    @Schema(description = "최근 체중(kg)", example = "9.2")
    private Double weight;

    @Schema(description = "체중 기록일", example = "2026-09-10")
    private LocalDate weightRecordedAt;

    @Schema(description = "알레르기 여부 (NONE: 없음, YES: 있음, UNKNOWN: 미입력)", example = "UNKNOWN")
    private AllergyStatus allergyStatus;

    @Schema(description = "알레르기 상세")
    private String allergyDetail;

    @Schema(description = "평소 복용하는 약")
    private String regularMedication;

    public static ChildProfileResponse from(Child child) {
        Period period = Period.between(child.getBirthDate(), LocalDate.now());
        int ageInMonths = period.getYears() * 12 + period.getMonths();

        return ChildProfileResponse.builder()
                .childId(child.getId())
                .name(child.getName())
                .birthDate(child.getBirthDate())
                .ageInMonths(ageInMonths)
                .weight(child.getWeight())
                .weightRecordedAt(child.getWeightRecordedAt())
                .allergyStatus(child.getAllergyStatus())
                .allergyDetail(child.getAllergyDetail())
                .regularMedication(child.getRegularMedication())
                .build();
    }
}

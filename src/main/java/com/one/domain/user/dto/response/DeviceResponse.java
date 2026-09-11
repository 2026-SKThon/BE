package com.one.domain.user.dto.response;

import com.one.domain.device.entity.Device;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "DeviceResponse: 연결 기기 응답 DTO")
public class DeviceResponse {

    @Schema(description = "기기 ID", example = "1")
    private Long deviceId;

    @Schema(description = "연결된 아이 ID", example = "1")
    private Long childId;

    @Schema(description = "연결된 아이 이름", example = "민호")
    private String childName;

    @Schema(description = "기기 코드", example = "ONE-A21")
    private String deviceCode;

    @Schema(description = "기기명", example = "온이 센서 A21")
    private String deviceName;

    @Schema(description = "연결 상태", example = "true")
    private boolean isConnected;

    @Schema(description = "마지막 연결(데이터 수신) 시각")
    private LocalDateTime lastConnectedAt;

    @Schema(description = "배터리 잔량(%), 정보 없으면 null")
    private Integer batteryLevel;

    public static DeviceResponse from(Device device) {
        return DeviceResponse.builder()
                .deviceId(device.getId())
                .childId(device.getChild().getId())
                .childName(device.getChild().getName())
                .deviceCode(device.getDeviceCode())
                .deviceName(device.getDeviceName())
                .isConnected(device.isConnected())
                .lastConnectedAt(device.getLastConnectedAt())
                .batteryLevel(device.getBatteryLevel())
                .build();
    }
}

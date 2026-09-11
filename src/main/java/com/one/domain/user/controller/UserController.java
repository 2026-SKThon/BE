package com.one.domain.user.controller;

import com.one.domain.user.dto.request.ChildProfileUpdateRequest;
import com.one.domain.user.dto.request.UserProfileUpdateRequest;
import com.one.domain.user.dto.response.ChildProfileResponse;
import com.one.domain.user.dto.response.DeviceResponse;
import com.one.domain.user.dto.response.UserProfileResponse;
import com.one.domain.user.service.UserService;
import com.one.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "User", description = "보호자 프로필, 아이 프로필, 연결 기기 조회/수정 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "프로필 조회 API", description = "userId로 보호자 프로필 정보를 조회하는 API")
    @GetMapping("/users/{userId}")
    public ResponseEntity<BaseResponse<UserProfileResponse>> getUserProfile(@PathVariable Long userId) {

        UserProfileResponse response = userService.getUserProfile(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "프로필 수정 API", description = "userId에 해당하는 보호자 프로필 정보를 부분 수정하는 API")
    @PatchMapping("/users/{userId}")
    public ResponseEntity<BaseResponse<UserProfileResponse>> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileUpdateRequest request) {

        UserProfileResponse response = userService.updateUserProfile(userId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }

    @Operation(
            summary = "아이 프로필 조회 API",
            description = "childId로 아이 프로필 정보를 조회하는 API. "
                    + "API 명세서상 경로는 /api/users/{childId} 였으나 프로필 조회(/api/users/{userId})와 "
                    + "경로 패턴이 동일해 매핑이 충돌하므로 /api/users/children/{childId} 로 구현했습니다."
    )
    @GetMapping("/users/children/{childId}")
    public ResponseEntity<BaseResponse<ChildProfileResponse>> getChildProfile(@PathVariable Long childId) {

        ChildProfileResponse response = userService.getChildProfile(childId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "아이 프로필 수정 API", description = "childId에 해당하는 아이 프로필 정보를 부분 수정하는 API")
    @PatchMapping("/users/children/{childId}")
    public ResponseEntity<BaseResponse<ChildProfileResponse>> updateChildProfile(
            @PathVariable Long childId,
            @RequestBody ChildProfileUpdateRequest request) {

        ChildProfileResponse response = userService.updateChildProfile(childId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "연결 기기 조회 API", description = "userId 보호자 계정의 대표 아이에게 연결된 기기 정보를 조회하는 API")
    @GetMapping("/users/{userId}/device")
    public ResponseEntity<BaseResponse<DeviceResponse>> getConnectedDevice(@PathVariable Long userId) {

        DeviceResponse response = userService.getConnectedDevice(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response));
    }
}

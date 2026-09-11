package com.one.domain.user.service;

import com.one.domain.child.entity.Child;
import com.one.domain.child.exception.ChildErrorCode;
import com.one.domain.child.repository.ChildRepository;
import com.one.domain.device.entity.Device;
import com.one.domain.device.repository.DeviceRepository;
import com.one.domain.user.dto.request.ChildProfileUpdateRequest;
import com.one.domain.user.dto.request.UserProfileUpdateRequest;
import com.one.domain.user.dto.response.ChildProfileResponse;
import com.one.domain.user.dto.response.DeviceResponse;
import com.one.domain.user.dto.response.UserProfileResponse;
import com.one.domain.user.entity.GuardianChild;
import com.one.domain.user.entity.User;
import com.one.domain.user.exception.UserErrorCode;
import com.one.domain.user.repository.GuardianChildRepository;
import com.one.domain.user.repository.UserRepository;
import com.one.global.exception.CommonErrorCode;
import com.one.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final GuardianChildRepository guardianChildRepository;
    private final DeviceRepository deviceRepository;

    public UserProfileResponse getUserProfile(Long userId) {
        User user = findUserOrThrow(userId);
        return UserProfileResponse.from(user);
    }

    public UserProfileResponse updateUserProfile(Long userId, UserProfileUpdateRequest request) {
        User user = findUserOrThrow(userId);
        user.updateProfile(request.getName(), request.getPhone());
        return UserProfileResponse.from(user);
    }

    public ChildProfileResponse getChildProfile(Long childId) {
        Child child = findChildOrThrow(childId);
        return ChildProfileResponse.from(child);
    }

    public ChildProfileResponse updateChildProfile(Long childId, ChildProfileUpdateRequest request) {
        Child child = findChildOrThrow(childId);
        child.updateProfile(
                request.getName(),
                request.getBirthDate(),
                request.getWeight(),
                request.getWeightRecordedAt(),
                request.getAllergyStatus(),
                request.getAllergyDetail(),
                request.getRegularMedication()
        );
        return ChildProfileResponse.from(child);
    }

    public DeviceResponse getConnectedDevice(Long userId) {
        findUserOrThrow(userId);

        GuardianChild guardianChild = guardianChildRepository.findFirstByUserIdOrderByRoleAsc(userId)
                .orElseThrow(() -> {
                    log.warn("[UserService] 등록된 아이가 없습니다. userId={}", userId);
                    return new CustomException(ChildErrorCode.CHILD_NOT_FOUND);
                });

        Device device = deviceRepository.findByChildId(guardianChild.getChild().getId())
                .orElseThrow(() -> {
                    log.warn("[UserService] 연결된 기기가 없습니다. userId={}", userId);
                    return new CustomException(CommonErrorCode.NOT_FOUND);
                });

        return DeviceResponse.from(device);
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.warn("[UserService] 존재하지 않는 유저입니다. userId={}", userId);
            return new CustomException(UserErrorCode.USER_NOT_FOUND);
        });
    }

    private Child findChildOrThrow(Long childId) {
        return childRepository.findById(childId).orElseThrow(() -> {
            log.warn("[UserService] 존재하지 않는 아이입니다. childId={}", childId);
            return new CustomException(ChildErrorCode.CHILD_NOT_FOUND);
        });
    }
}

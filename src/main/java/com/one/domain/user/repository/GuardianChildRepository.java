package com.one.domain.user.repository;

import com.one.domain.user.entity.GuardianChild;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianChildRepository extends JpaRepository<GuardianChild, Long> {

    // user가 관리하는 아이인지 확인
    boolean existsByUserIdAndChildId(Long userId, Long childId);

    // user의 대표 아이(주 보호자 우선) 조회 - 연결 기기 조회 등에 사용
    Optional<GuardianChild> findFirstByUserIdOrderByRoleAsc(Long userId);

}

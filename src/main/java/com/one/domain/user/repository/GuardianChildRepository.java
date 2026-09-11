package com.one.domain.user.repository;

import com.one.domain.user.entity.GuardianChild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianChildRepository extends JpaRepository<GuardianChild, Long> {

    // user가 관리하는 아이인지 확인
    boolean existsByUserIdAndChildId(Long userId, Long childId);

}

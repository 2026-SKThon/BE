package com.one.domain.fever.repository;

import com.one.domain.child.entity.Child;
import com.one.domain.fever.entity.FeverEpisode;
import com.one.domain.fever.enums.FeverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeverRepository extends JpaRepository<FeverEpisode, Long> {

    Optional<FeverEpisode> findByChildAndStatus(Child child, FeverStatus feverStatus);
}

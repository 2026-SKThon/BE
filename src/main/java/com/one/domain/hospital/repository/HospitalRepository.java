package com.one.domain.hospital.repository;

import com.one.domain.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalQueryRepository {

    @Query("select h from Hospital h left join fetch h.schedules where h.id = :id")
    Optional<Hospital> findByIdWithSchedules(@Param("id") Long id);
}

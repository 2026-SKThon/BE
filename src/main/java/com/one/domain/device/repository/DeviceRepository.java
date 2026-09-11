package com.one.domain.device.repository;

import com.one.domain.device.entity.Device;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByChildId(Long childId);
}

package com.raina.vehicle_inspection.repository;

import com.raina.vehicle_inspection.dao.DamageDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageDetailsRepository extends JpaRepository<DamageDetails, Long> { }

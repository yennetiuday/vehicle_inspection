package com.raina.vehicle_inspection.service;

import com.raina.vehicle_inspection.vo.DamageDetailRequest;
import com.raina.vehicle_inspection.vo.DamageDetailResponse;

public interface DamageDetailsService {

    DamageDetailResponse getDamageDetails(Long id);

    DamageDetailResponse saveDamageDetails(DamageDetailRequest damageDetailRequest);

    DamageDetailResponse updateDamageDetails(Long id, DamageDetailRequest damageDetailRequest);

    void deleteDamageDetails(Long id);
}

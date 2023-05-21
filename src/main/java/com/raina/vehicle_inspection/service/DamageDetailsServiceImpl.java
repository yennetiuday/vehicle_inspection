package com.raina.vehicle_inspection.service;

import com.raina.vehicle_inspection.dao.DamageDetails;
import com.raina.vehicle_inspection.dao.Vehicle;
import com.raina.vehicle_inspection.exception.NoDataFoundException;
import com.raina.vehicle_inspection.repository.DamageDetailsRepository;
import com.raina.vehicle_inspection.repository.VehicleRepository;
import com.raina.vehicle_inspection.vo.DamageDetailRequest;
import com.raina.vehicle_inspection.vo.DamageDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DamageDetailsServiceImpl implements DamageDetailsService {

  @Autowired private final VehicleRepository vehicleRepository;
  @Autowired private final DamageDetailsRepository damageDetailsRepository;

  @Override
  public DamageDetailResponse getDamageDetails(Long id) {
    return getDamageDetailsById(id).toVO();
  }

  private DamageDetails getDamageDetailsById(Long id) {
    return damageDetailsRepository.findById(id)
            .orElseThrow(() -> new NoDataFoundException("Damage details data not found with ID: " + id));
  }

  @Override
  public DamageDetailResponse saveDamageDetails(DamageDetailRequest damageDetailRequest) {
    DamageDetails damageDetails = damageDetailRequest.toDAO();
    Vehicle vehicle = getVehicle(damageDetailRequest.getVehicleId());
    damageDetails.setVehicle(vehicle);
    return damageDetailsRepository.save(damageDetails).toVO();
  }

  private Vehicle getVehicle(Long vehicleId) {
    return vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new NoDataFoundException("Vehicle data not found with ID: " + vehicleId));
  }

    @Override
  public DamageDetailResponse updateDamageDetails(Long id, DamageDetailRequest damageDetailRequest) {
    DamageDetails damageDetails = getDamageDetailsById(id);
    Vehicle vehicle = getVehicle(damageDetailRequest.getVehicleId());
    damageDetails.setVehicle(vehicle);
    damageDetails.setDamageType(damageDetailRequest.getDamageType());
    damageDetails.setDamageDescription(damageDetailRequest.getDamageDescription());
    return damageDetailsRepository.save(damageDetails).toVO();
  }

  @Override
  public void deleteDamageDetails(Long id) {
    damageDetailsRepository.delete(getDamageDetailsById(id));
  }
}

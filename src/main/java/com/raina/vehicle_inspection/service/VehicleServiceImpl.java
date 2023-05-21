package com.raina.vehicle_inspection.service;

import com.raina.vehicle_inspection.dao.Vehicle;
import com.raina.vehicle_inspection.exception.NoDataFoundException;
import com.raina.vehicle_inspection.exception.VehicleInspectionException;
import com.raina.vehicle_inspection.repository.VehicleRepository;
import com.raina.vehicle_inspection.vo.VehicleRequest;
import com.raina.vehicle_inspection.vo.VehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

  @Autowired private final VehicleRepository vehicleRepository;

  @Override
  public VehicleResponse getVehicleById(Long id) {
    return getVehicle(id).toVO();
  }

  private Vehicle getVehicle(Long id) {
    return vehicleRepository
            .findById(id)
            .orElseThrow(() -> new NoDataFoundException("Vehicle data not found with ID: " + id));
  }

  @Override
  public VehicleResponse saveVehicle(VehicleRequest vehicleRequest) {
    try {
      return vehicleRepository.save(vehicleRequest.toDao()).toVO();
    } catch (DataIntegrityViolationException e) {
      throw new VehicleInspectionException("Registration number already exists");
    }
  }

  @Override
  public VehicleResponse updateVehicle(Long id, VehicleRequest vehicleRequest) {
    Vehicle vehicle = getVehicle(id);
    vehicle.setName(vehicleRequest.getName());
    vehicle.setModel(vehicleRequest.getModel());
    vehicle.setRegistrationNumber(vehicleRequest.getRegistrationNumber());
    try {
      return vehicleRepository.save(vehicle).toVO();
    } catch (DataIntegrityViolationException e) {
      throw new VehicleInspectionException("Registration number already exists");
    }
  }

  @Override
  public void deleteVehicle(Long id) {
    vehicleRepository.delete(getVehicle(id));
  }
}

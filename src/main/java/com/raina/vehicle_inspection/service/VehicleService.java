package com.raina.vehicle_inspection.service;

import com.raina.vehicle_inspection.vo.VehicleRequest;
import com.raina.vehicle_inspection.vo.VehicleResponse;

public interface VehicleService {

    VehicleResponse getVehicleById(Long id);

    VehicleResponse saveVehicle(VehicleRequest vehicleRequest);

    VehicleResponse updateVehicle(Long id, VehicleRequest vehicleRequest);

    void deleteVehicle(Long id);
}

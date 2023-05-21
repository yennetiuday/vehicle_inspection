package com.raina.vehicle_inspection.controller;

import com.raina.vehicle_inspection.exception.NoDataFoundException;
import com.raina.vehicle_inspection.exception.VehicleInspectionException;
import com.raina.vehicle_inspection.service.VehicleService;
import com.raina.vehicle_inspection.vo.VehicleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    @Autowired
    private final VehicleService vehicleService;

    @GetMapping("{id}")
    public ResponseEntity<?> getVehicle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vehicleService.getVehicleById(id));
        } catch (NoDataFoundException e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(601).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(605).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveVehicle(@Valid @RequestBody VehicleRequest vehicleRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            log.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        try {
            return ResponseEntity.ok(vehicleService.saveVehicle(vehicleRequest));
        } catch (VehicleInspectionException e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(602).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(605).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id,
                                           @Valid @RequestBody VehicleRequest vehicleRequest,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            log.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        try {
            return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleRequest));
        } catch (NoDataFoundException e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(601).body(e.getMessage());
        } catch (VehicleInspectionException e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(602).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(605).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        try {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (NoDataFoundException e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(601).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(605).body(e.getMessage());
        }
    }
}

package com.raina.vehicle_inspection.controller;

import com.raina.vehicle_inspection.exception.NoDataFoundException;
import com.raina.vehicle_inspection.service.DamageDetailsService;
import com.raina.vehicle_inspection.service.VehicleService;
import com.raina.vehicle_inspection.vo.DamageDetailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/damageDetails")
@RequiredArgsConstructor
@Slf4j
public class DamageDetailsController {

  @Autowired
  private final VehicleService vehicleService;
  @Autowired
  private final DamageDetailsService damageDetailsService;

  @GetMapping("vehicle/{id}")
  public ResponseEntity<?> getDamageReport(@PathVariable("id") Long id) {
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

  @GetMapping("/{id}")
  public ResponseEntity<?> getDamageDetails(@PathVariable("id") Long id) {
    try {
      return ResponseEntity.ok(damageDetailsService.getDamageDetails(id));
    } catch (NoDataFoundException e) {
      log.error(e.getMessage(), e.getStackTrace());
      return ResponseEntity.status(601).body(e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage(), e.getStackTrace());
      return ResponseEntity.status(605).body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> saveDamageDetails(@Valid @RequestBody DamageDetailRequest damageDetailRequest,
                                             BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String errorMessage = bindingResult.getFieldError().getDefaultMessage();
      log.error(errorMessage);
      return ResponseEntity.badRequest().body(errorMessage);
    }
    try {
      return ResponseEntity.ok(damageDetailsService.saveDamageDetails(damageDetailRequest));
    } catch (NoDataFoundException e) {
      log.error(e.getMessage(), e.getStackTrace());
      return ResponseEntity.status(601).body(e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage(), e.getStackTrace());
      return ResponseEntity.status(605).body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<?> updateDamageDetails(@PathVariable("id") Long id,
                                               @Valid @RequestBody DamageDetailRequest damageDetailRequest,
                                               BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String errorMessage = bindingResult.getFieldError().getDefaultMessage();
      log.error(errorMessage);
      return ResponseEntity.badRequest().body(errorMessage);
    }
    try {
      return ResponseEntity.ok(damageDetailsService.updateDamageDetails(id, damageDetailRequest));
    } catch (NoDataFoundException e) {
      log.error(e.getMessage(), e.getStackTrace());
      return ResponseEntity.status(601).body(e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage(), e.getStackTrace());
      return ResponseEntity.status(605).body(e.getMessage());
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteDamageDetails(@PathVariable("id") Long id) {
    try {
      damageDetailsService.deleteDamageDetails(id);
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

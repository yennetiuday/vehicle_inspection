package com.raina.vehicle_inspection.vo;

import com.raina.vehicle_inspection.dao.DamageDetails;
import com.raina.vehicle_inspection.dao.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DamageDetailRequest {

  @NotNull(message = "Vehicle id is required")
  private Long vehicleId;
  @NotNull(message = "Damage type is required")
  private String damageType;
  private String damageDescription;

  public DamageDetails toDAO() {
    return new DamageDetails()
        .builder()
        .damageType(damageType)
        .vehicle(new Vehicle().builder().id(vehicleId).build())
        .damageDescription(damageDescription)
        .build();
  }
}

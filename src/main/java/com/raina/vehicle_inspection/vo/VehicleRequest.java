package com.raina.vehicle_inspection.vo;

import com.raina.vehicle_inspection.dao.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequest {

  @NotNull(message = "Vehicle name is required")
  private String name;
  @NotNull(message = "Vehicle model is required")
  private String model;
  @NotNull(message = "Vehicle registration number is required")
  private String registrationNumber;

  public Vehicle toDao() {
    return new Vehicle()
        .builder()
        .name(name)
        .model(model)
        .registrationNumber(registrationNumber)
        .build();
  }
}

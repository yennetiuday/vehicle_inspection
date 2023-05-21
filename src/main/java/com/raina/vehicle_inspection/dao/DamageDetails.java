package com.raina.vehicle_inspection.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raina.vehicle_inspection.vo.DamageDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DamageDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "vehicle_id", nullable = false)
  private Vehicle vehicle;
  private String damageType;
  private String damageDescription;

  public DamageDetailResponse toVO() {
    return new DamageDetailResponse()
        .builder()
        .id(id)
        .damageType(damageType)
        .damageDescription(damageDescription)
        .vehicleId(vehicle.getId())
        .build();
  }

}

package com.raina.vehicle_inspection.dao;

import com.raina.vehicle_inspection.vo.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String model;
  @Column(unique = true)
  private String registrationNumber;
  @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<DamageDetails> damageDetailsList;

  public VehicleResponse toVO() {
    if (Objects.isNull(damageDetailsList)) {
      damageDetailsList = new ArrayList<>();
    }
    return new VehicleResponse()
        .builder()
        .id(id)
        .name(name)
        .model(model)
        .registrationNumber(registrationNumber)
        .damageDetails(new ArrayList<>(damageDetailsList).stream()
                .map(damageDetails -> damageDetails.toVO()).toList())
        .build();
  }
}

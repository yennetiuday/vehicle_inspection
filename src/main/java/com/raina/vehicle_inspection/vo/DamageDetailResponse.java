package com.raina.vehicle_inspection.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DamageDetailResponse {
    private Long id;
    private Long vehicleId;
    private String damageType;
    private String damageDescription;
}

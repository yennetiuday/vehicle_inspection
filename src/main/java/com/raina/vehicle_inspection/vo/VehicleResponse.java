package com.raina.vehicle_inspection.vo;

import com.raina.vehicle_inspection.dao.DamageDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponse {
    private Long id;
    private String name;
    private String model;
    private String registrationNumber;
    private List<DamageDetailResponse> damageDetails;
}

package com.raina.vehicle_inspection.service;

import com.raina.vehicle_inspection.dao.DamageDetails;
import com.raina.vehicle_inspection.dao.Vehicle;
import com.raina.vehicle_inspection.exception.NoDataFoundException;
import com.raina.vehicle_inspection.repository.DamageDetailsRepository;
import com.raina.vehicle_inspection.repository.VehicleRepository;
import com.raina.vehicle_inspection.vo.DamageDetailRequest;
import com.raina.vehicle_inspection.vo.DamageDetailResponse;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class DamageDetailsServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private DamageDetailsRepository damageDetailsRepository;
    @InjectMocks
    private DamageDetailsServiceImpl damageDetailsService;

    @Test
    void getDamageDetails() {
        DamageDetailResponse expectedResponse = getMockDamageDetail().get().toVO();
        Mockito.when(damageDetailsRepository.findById(Mockito.anyLong())).thenReturn(getMockDamageDetail());

        DamageDetailResponse actualResponse = damageDetailsService.getDamageDetails(Long.valueOf(1));
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void getDamageDetailsErrorCase() {
        Mockito.when(damageDetailsRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try {
            damageDetailsService.getDamageDetails(Long.valueOf(1));
        } catch (NoDataFoundException e) {
            Assert.assertEquals("Damage details data not found with ID: 1", e.getMessage());
        }
    }

    @Test
    void saveDamageDetails() {
        DamageDetailResponse expectedResponse = getMockDamageDetail().get().toVO();
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(getMockVehicle());
        Mockito.when(damageDetailsRepository.save(Mockito.any())).thenReturn(getMockDamageDetail().get());

        DamageDetailResponse actualResponse = damageDetailsService.saveDamageDetails(getMockRequest());
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void saveDamageDetailsErrorCase() {
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try {
            damageDetailsService.saveDamageDetails(getMockRequest());
        } catch (NoDataFoundException e) {
            Assert.assertEquals("Vehicle data not found with ID: 1", e.getMessage());
        }
    }

    @Test
    void updateDamageDetails() {
        DamageDetailResponse expectedResponse = getMockDamageDetail().get().toVO();
        Mockito.when(damageDetailsRepository.findById(Mockito.anyLong())).thenReturn(getMockDamageDetail());
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(getMockVehicle());
        Mockito.when(damageDetailsRepository.save(Mockito.any())).thenReturn(getMockDamageDetail().get());

        DamageDetailResponse actualResponse = damageDetailsService
                .updateDamageDetails(Long.valueOf(1),getMockRequest());
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void updateDamageDetailsErrorCaseInvalidId() {
        Mockito.when(damageDetailsRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        try {
            damageDetailsService.updateDamageDetails(Long.valueOf(1),getMockRequest());
        } catch (NoDataFoundException e) {
            Assert.assertEquals("Damage details data not found with ID: 1", e.getMessage());
        }
    }

    @Test
    void updateDamageDetailsErrorCaseInvalidVehicleId() {
        Mockito.when(damageDetailsRepository.findById(Mockito.anyLong())).thenReturn(getMockDamageDetail());
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        try {
            damageDetailsService.updateDamageDetails(Long.valueOf(1),getMockRequest());
        } catch (NoDataFoundException e) {
            Assert.assertEquals("Vehicle data not found with ID: 1", e.getMessage());
        }
    }

    @Test
    void deleteDamageDetails() {
        DamageDetailResponse expectedResponse = getMockDamageDetail().get().toVO();
        Mockito.when(damageDetailsRepository.findById(Mockito.anyLong())).thenReturn(getMockDamageDetail());
        Mockito.doNothing().when(damageDetailsRepository).delete(Mockito.any());

        damageDetailsService.deleteDamageDetails(Long.valueOf(1));
        Mockito.verify(damageDetailsRepository, Mockito.times(1))
                .delete(getMockDamageDetail().get());
        Mockito.verify(damageDetailsRepository, Mockito.times(1)).findById(Long.valueOf(1));
    }

    @Test
    void deleteDamageDetailsErrorCase() {
        Mockito.when(damageDetailsRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try {
            damageDetailsService.getDamageDetails(Long.valueOf(1));
        } catch (NoDataFoundException e) {
            Assert.assertEquals("Damage details data not found with ID: 1", e.getMessage());
        }
    }

    private DamageDetailRequest getMockRequest() {
        return new DamageDetailRequest()
                .builder()
                .vehicleId(Long.valueOf(1))
                .damageType("damageType")
                .damageDescription("damageDescription")
                .build();
    }

    private Optional<DamageDetails> getMockDamageDetail() {
        return Optional.of(new DamageDetails()
                .builder()
                .id(Long.valueOf(1))
                .vehicle(getMockVehicle().get())
                .damageType("damageType")
                .damageDescription("damageDescription")
                .build());
    }

    private Optional<Vehicle> getMockVehicle() {
        return Optional.of(new Vehicle()
                .builder()
                .id(Long.valueOf(1))
                .name("Name")
                .registrationNumber("RegistrationNumber")
                .model("Model")
                .build());
    }
}
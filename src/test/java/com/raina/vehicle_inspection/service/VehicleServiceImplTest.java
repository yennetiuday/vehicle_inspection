package com.raina.vehicle_inspection.service;

import com.raina.vehicle_inspection.dao.Vehicle;
import com.raina.vehicle_inspection.exception.NoDataFoundException;
import com.raina.vehicle_inspection.exception.VehicleInspectionException;
import com.raina.vehicle_inspection.repository.VehicleRepository;
import com.raina.vehicle_inspection.vo.VehicleRequest;
import com.raina.vehicle_inspection.vo.VehicleResponse;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class VehicleServiceImplTest {

  @Mock private VehicleRepository vehicleRepository;
  @InjectMocks private VehicleServiceImpl vehicleService;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetVehicleById() {
    VehicleResponse expectedResponse = getMockVehicle().get().toVO();
    Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(getMockVehicle());

    VehicleResponse actualResponse = vehicleService.getVehicleById(Long.valueOf(1));
    Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  public void testGetVehicleByIdErrorCase() throws Exception {
    Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    try {
      vehicleService.getVehicleById(1L);
    } catch (NoDataFoundException e) {
      Assert.assertEquals("Vehicle data not found with ID: 1", e.getMessage());
    }
  }

  @Test
  void testSaveVehicle() {
    VehicleResponse expectedResponse = getMockVehicle().get().toVO();
    Mockito.when(vehicleRepository.save(Mockito.any())).thenReturn(getMockVehicle().get());

    VehicleResponse actualResponse = vehicleService.saveVehicle(getMockVehicleRequest());
    Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void testSaveVehicleErrorCase() {
    Mockito.when(vehicleRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);
    try {
      vehicleService.saveVehicle(getMockVehicleRequest());
    } catch (VehicleInspectionException e) {
      Assert.assertEquals("Registration number already exists", e.getMessage());
    }
  }

  @Test
  void testUpdateVehicle() {
    VehicleResponse expectedResponse = getMockVehicle().get().toVO();
    Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(getMockVehicle());
    Mockito.when(vehicleRepository.save(Mockito.any())).thenReturn(getMockVehicle().get());

    VehicleResponse actualResponse = vehicleService.updateVehicle(Long.valueOf(1), getMockVehicleRequest());
    Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void testUpdateVehicleErrorCase() {
    Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    try {
      vehicleService.updateVehicle(Long.valueOf(1), getMockVehicleRequest());
    } catch (NoDataFoundException e) {
      Assert.assertEquals("Vehicle data not found with ID: 1", e.getMessage());
    }
  }

  @Test
  void testUpdateVehicleErrorCase_DataIntegrityViolationException() {
    Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(getMockVehicle());
    Mockito.when(vehicleRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

    try {
      vehicleService.updateVehicle(Long.valueOf(1), getMockVehicleRequest());
    } catch (VehicleInspectionException e) {
      Assert.assertEquals("Registration number already exists", e.getMessage());
    }
  }

  @Test
  void testDeleteVehicle() {
    VehicleResponse expectedResponse = getMockVehicle().get().toVO();
    Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(getMockVehicle());
    Mockito.doNothing().when(vehicleRepository).delete(Mockito.any());

    vehicleService.deleteVehicle(Long.valueOf(1));
    Mockito.verify(vehicleRepository, Mockito.times(1)).delete(getMockVehicle().get());
    Mockito.verify(vehicleRepository, Mockito.times(1)).findById(Long.valueOf(1));
  }

  @Test
  void testDeleteVehicleErrorCase() {
    Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    try {
      vehicleService.deleteVehicle(Long.valueOf(1));
    } catch (NoDataFoundException e) {
      Assert.assertEquals("Vehicle data not found with ID: 1", e.getMessage());
    }
  }

  private Optional<Vehicle> getMockVehicle() {
    return Optional.of(
        new Vehicle()
            .builder()
            .id(Long.valueOf(1))
            .name("Name")
            .registrationNumber("RegistrationNumber")
            .model("Model")
            .build());
  }

  private VehicleRequest getMockVehicleRequest() {
    return new VehicleRequest()
        .builder()
        .name("Name")
        .model("Model")
        .registrationNumber("RegistrationNumber")
        .build();
  }
}

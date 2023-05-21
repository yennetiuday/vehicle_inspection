package com.raina.vehicle_inspection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.raina.vehicle_inspection.exception.NoDataFoundException;
import com.raina.vehicle_inspection.exception.VehicleInspectionException;
import com.raina.vehicle_inspection.service.VehicleService;
import com.raina.vehicle_inspection.vo.VehicleRequest;
import com.raina.vehicle_inspection.vo.VehicleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class VehicleControllerTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @Autowired
    private MockMvc mockMvcController;

    @BeforeEach
    public void setUp() {
        mockMvcController = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }

    @Test
    void testGetVehicle() throws Exception {
        Mockito.when(vehicleService.getVehicleById(Mockito.anyLong())).thenReturn(getMockVehicleResponse());

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetVehicle_NoDataFoundException() throws Exception {
        Mockito.when(vehicleService.getVehicleById(Mockito.anyLong())).thenThrow(NoDataFoundException.class);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().is(601));
    }

    @Test
    void saveVehicle() throws Exception {
        Mockito.when(vehicleService.saveVehicle(Mockito.any())).thenReturn(getMockVehicleResponse());

        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getMockVehicleRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveVehicle_VehicleInspectionException() throws Exception {
        Mockito.when(vehicleService.saveVehicle(Mockito.any())).thenThrow(VehicleInspectionException.class);

        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getMockVehicleRequest())))
                .andExpect(MockMvcResultMatchers.status().is(602));
    }

    @Test
    void updateVehicle() throws Exception {
        Mockito.when(vehicleService.updateVehicle(Mockito.anyLong(),Mockito.any())).thenReturn(getMockVehicleResponse());

        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/vehicle/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getMockVehicleRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateVehicle_NoDataFoundException() throws Exception {
        Mockito.when(vehicleService.updateVehicle(Mockito.anyLong(),Mockito.any())).thenThrow(NoDataFoundException.class);

        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/vehicle/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getMockVehicleRequest())))
                .andExpect(MockMvcResultMatchers.status().is(601));
    }

    @Test
    void updateVehicle_VehicleInspectionException() throws Exception {
        Mockito.when(vehicleService.updateVehicle(Mockito.anyLong(),Mockito.any())).thenThrow(VehicleInspectionException.class);

        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/vehicle/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getMockVehicleRequest())))
                .andExpect(MockMvcResultMatchers.status().is(602));
    }

    @Test
    void deleteVehicle() throws Exception {
        Mockito.doNothing().when(vehicleService).deleteVehicle(Mockito.anyLong());

        this.mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteVehicle_NoDataFoundException() throws Exception {
        Mockito.doThrow(NoDataFoundException.class).when(vehicleService).deleteVehicle(Mockito.anyLong());

        this.mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().is(601));
    }

    private VehicleResponse getMockVehicleResponse() {
        return new VehicleResponse().builder()
                .id(Long.valueOf(1))
                .name("name")
                .model("model")
                .registrationNumber("registrationNumber")
                .build();
    }

    private VehicleRequest getMockVehicleRequest() {
        return new VehicleRequest().builder()
                .name("name")
                .model("model")
                .registrationNumber("registrationNumber")
                .build();
    }

    private static String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
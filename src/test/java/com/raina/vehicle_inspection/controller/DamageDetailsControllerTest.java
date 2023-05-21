package com.raina.vehicle_inspection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.raina.vehicle_inspection.dao.DamageDetails;
import com.raina.vehicle_inspection.exception.NoDataFoundException;
import com.raina.vehicle_inspection.service.DamageDetailsService;
import com.raina.vehicle_inspection.service.VehicleService;
import com.raina.vehicle_inspection.vo.DamageDetailRequest;
import com.raina.vehicle_inspection.vo.DamageDetailResponse;
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

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
public class DamageDetailsControllerTest {

    @Mock
    private VehicleService vehicleService;
    @Mock
    private DamageDetailsService damageDetailsService;

    @InjectMocks
    private DamageDetailsController damageDetailsController;

    @Autowired
    private MockMvc mockMvcController;

    @BeforeEach
    public void setUp() {
        mockMvcController = MockMvcBuilders.standaloneSetup(damageDetailsController).build();
    }

    @Test
    void testGetDamageReport() throws Exception {
        Mockito.when(vehicleService.getVehicleById(Mockito.anyLong())).thenReturn(getMockVehicleResponse());

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/damageDetails/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetDamageReport_NoDataFoundException() throws Exception {
        Mockito.when(vehicleService.getVehicleById(Mockito.anyLong())).thenThrow(NoDataFoundException.class);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/damageDetails/vehicle/1"))
                .andExpect(MockMvcResultMatchers.status().is(601));
    }

    @Test
    void testGetDamageDetails() throws Exception {
        Mockito.when(damageDetailsService.getDamageDetails(Mockito.anyLong()))
                .thenReturn(getMockDamageDetailResponse());

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/damageDetails/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetDamageDetails_NoDataFoundException() throws Exception {
        Mockito.when(damageDetailsService.getDamageDetails(Mockito.anyLong()))
                .thenThrow(NoDataFoundException.class);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/damageDetails/1"))
                .andExpect(MockMvcResultMatchers.status().is(601));
    }

    @Test
    void testSaveDamageDetails() throws Exception {
        Mockito.when(damageDetailsService.saveDamageDetails(getMockDamageDetailRequest()))
                .thenReturn(getMockDamageDetailResponse());

        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/damageDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getMockDamageDetailRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSaveDamageDetails_NoDataFoundException() throws Exception {
        Mockito.when(damageDetailsService.saveDamageDetails(getMockDamageDetailRequest()))
                .thenThrow(NoDataFoundException.class);

        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/damageDetails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getMockDamageDetailRequest())))
                .andExpect(MockMvcResultMatchers.status().is(601));
    }

    @Test
    void updateDamageDetails() throws Exception {
        Mockito.when(damageDetailsService.updateDamageDetails(Long.valueOf(1), getMockDamageDetailRequest()))
                .thenReturn(getMockDamageDetailResponse());

        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/damageDetails/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getMockDamageDetailRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateDamageDetails_NoDataFoundException() throws Exception {
        Mockito.when(damageDetailsService.updateDamageDetails(Long.valueOf(1), getMockDamageDetailRequest()))
                .thenThrow(NoDataFoundException.class);

        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/damageDetails/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getMockDamageDetailRequest())))
                .andExpect(MockMvcResultMatchers.status().is(601));
    }

    @Test
    void deleteDamageDetails() throws Exception {
        Mockito.doNothing().when(damageDetailsService).deleteDamageDetails(Mockito.anyLong());

        this.mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/damageDetails/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteDamageDetails_() throws Exception {
        Mockito.doThrow(NoDataFoundException.class).when(damageDetailsService).deleteDamageDetails(Mockito.anyLong());

        this.mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/damageDetails/1"))
                .andExpect(MockMvcResultMatchers.status().is(601));
    }

    private VehicleResponse getMockVehicleResponse() {
        DamageDetailResponse mockDamageDetails = getMockDamageDetailResponse();
        DamageDetailResponse mockDamageDetails2 = new DamageDetailResponse().builder()
                .id(Long.valueOf(2))
                .vehicleId(Long.valueOf(1))
                .damageType("damageType2")
                .damageDescription("damageDescription2")
                .build();
        return new VehicleResponse().builder()
                .id(Long.valueOf(1))
                .name("name")
                .model("model")
                .registrationNumber("registrationNumber")
                .damageDetails(List.of(mockDamageDetails, mockDamageDetails2))
                .build();
    }

    private DamageDetailRequest getMockDamageDetailRequest() {
        return new DamageDetailRequest().builder()
                .damageType("damageType")
                .damageDescription("damageDescription")
                .vehicleId(Long.valueOf(1))
                .build();
    }

    private DamageDetailResponse getMockDamageDetailResponse() {
        return new DamageDetailResponse().builder()
                .id(Long.valueOf(1))
                .vehicleId(Long.valueOf(1))
                .damageType("damageType")
                .damageDescription("damageDescription")
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
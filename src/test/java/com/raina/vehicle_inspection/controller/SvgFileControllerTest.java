package com.raina.vehicle_inspection.controller;

import com.raina.vehicle_inspection.dao.SvgFile;
import com.raina.vehicle_inspection.service.SvgFileService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
@SpringBootTest
public class SvgFileControllerTest {

    @Mock
    private SvgFileService svgFileService;

    @InjectMocks
    private SvgFileController svgFileController;

    @Autowired
    private MockMvc mockMvcController;

    @Test
    public void testUploadSvgFile() throws Exception {
        Mockito.when(svgFileService.saveSvgFile(Mockito.any())).thenReturn(new SvgFile());

        MockMultipartFile svgFile = new MockMultipartFile("svgFile",
                "test.svg", "image/svg+xml", "<svg>...</svg>".getBytes());

        mockMvcController.perform(MockMvcRequestBuilders.multipart("/api/v1/svgFile/upload")
                .file(svgFile))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

package com.raina.vehicle_inspection.controller;

import com.raina.vehicle_inspection.dao.SvgFile;
import com.raina.vehicle_inspection.service.SvgFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/svgFile")
@RequiredArgsConstructor
@Slf4j
public class SvgFileController {

    @Autowired
    private final SvgFileService svgFileService;

    @PostMapping("upload")
    private ResponseEntity<?> uploadSvgFile(@RequestParam("svgFile") MultipartFile svgFile) {
        try {
            byte[] svgBytes = svgFile.getBytes();
            String svgContent = new String(svgBytes, StandardCharsets.UTF_8);

            SvgFile svgFileRequest = new SvgFile().builder().svgContent(svgContent).build();

            svgFileService.saveSvgFile(svgFileRequest);

            return ResponseEntity.ok("Svg file uploaded successfully");
        } catch (IOException e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(603).body("Issues in uploaded file");
        } catch (Exception e) {
            log.error(e.getMessage(), e.getStackTrace());
            return ResponseEntity.status(605).body(e.getMessage());
        }
    }

}

package com.raina.vehicle_inspection.service;

import com.raina.vehicle_inspection.dao.SvgFile;
import com.raina.vehicle_inspection.repository.SvgFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SvgFileServiceImpl implements SvgFileService {

    @Autowired
    private final SvgFileRepository svgFileRepository;

    @Override
    public SvgFile saveSvgFile(SvgFile svgFile) {
        return svgFileRepository.save(svgFile);
    }
}

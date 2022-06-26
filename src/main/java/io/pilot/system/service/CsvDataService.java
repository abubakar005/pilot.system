package io.pilot.system.service;

import org.springframework.web.multipart.MultipartFile;

public interface CsvDataService {

    void saveCSVData(MultipartFile file) throws Exception;
}

package io.pilot.system.resource;

import io.pilot.system.dto.ResponseDto;
import io.pilot.system.enums.Error;
import io.pilot.system.exception.BadRequestException;
import io.pilot.system.service.CsvDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/csv")
@Slf4j
public class CsvDataController {

    @Autowired
    private CsvDataService csvDataService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    /** This API is for importing CSV file into database via Spring Batch, need to pass CSV file path */
    @PostMapping("/batch")
    public BatchStatus processBatchFile(@RequestParam("filePath") String filePath) {

        JobExecution jobExecution;

        try {
            Map<String, JobParameter> map = new HashMap<>();
            map.put("filePath", new JobParameter(filePath));
            JobParameters parameters = new JobParameters(map);

            log.info("Batch is Starting...");
            jobExecution = jobLauncher.run(job, parameters);
            log.info("Batch Status after completion: " + jobExecution.getStatus());

            return jobExecution.getStatus();

        } catch (Exception e) {

            log.error("Exception " + e);

            if (e instanceof JobInstanceAlreadyCompleteException)
                throw new BadRequestException(Error.GENERAL_EXCEPTION.getCode(), e.getMessage());

            throw new BadRequestException(Error.GENERAL_EXCEPTION.getCode(), Error.GENERAL_EXCEPTION.getMsg());
        }
    }

    /** This API is for importing CSV file into database via uploading file and it will use parallel stream for insertion */
    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        csvDataService.saveCSVData(file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(Error.FILE_UPLOADED_SUCCESSFULLY.getCode(), String.format(Error.FILE_UPLOADED_SUCCESSFULLY.getMsg(), file.getOriginalFilename())));
    }
}

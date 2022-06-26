package io.pilot.system.service.impl;

import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.enums.Error;
import io.pilot.system.exception.BadRequestException;
import io.pilot.system.service.CsvDataService;
import io.pilot.system.service.TedTalkService;
import io.pilot.system.util.CSVParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CsvDataServiceImpl implements CsvDataService {

    @Autowired
    private TedTalkService tedTalkService;

    @Override
    public void saveCSVData(MultipartFile file) throws Exception {

        // Checking the file format if it is CSV
        if (!CSVParserUtil.hasCSVFormat(file))
            throw new BadRequestException(Error.INVALID_FILE_FORMAT.getCode(), Error.INVALID_FILE_FORMAT.getMsg());

        List<CreateTedTalkRequestDto> tedTalkRequests = CSVParserUtil.csvToTedTalkRequestDto(file.getInputStream());

        tedTalkRequests.parallelStream()
                .forEach(t -> tedTalkService.createTedTalk(t));
    }
}

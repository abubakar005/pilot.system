package io.pilot.system.util;

import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.enums.Error;
import io.pilot.system.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CSVParserUtil {

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!Constants.FILE_TYPE.equals(file.getContentType()))
            return false;

        return true;
    }

    // Reading CSV file and then converting into the 'CreateTedTalkRequestDto' type object
    public static List<CreateTedTalkRequestDto> csvToTedTalkRequestDto(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            return csvParser.getRecords().parallelStream()
                    .map(csvRecord -> CreateTedTalkRequestDto
                            .builder()
                            .title(csvRecord.get(Constants.FILE_HEADER_TITLE))
                            .author(csvRecord.get(Constants.FILE_HEADER_AUTHOR))
                            .date(csvRecord.get(Constants.FILE_HEADER_DATE))
                            .views(Long.valueOf(csvRecord.get(Constants.FILE_HEADER_VIEWS)))
                            .likes(Long.valueOf(csvRecord.get(Constants.FILE_HEADER_LIKES)))
                            .link(csvRecord.get(Constants.FILE_HEADER_LINK))
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Exception ", e);
            throw new BadRequestException(Error.FILE_PARSING_FAILED.getCode(), Error.FILE_PARSING_FAILED.getMsg());
        }
    }
}

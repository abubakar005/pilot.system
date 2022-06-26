package io.pilot.system.config;

import io.pilot.system.model.TedTalk;
import io.pilot.system.util.Constants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<TedTalk> itemReader,
                   ItemProcessor<TedTalk, TedTalk> itemProcessor,
                   ItemWriter<TedTalk> itemWriter) {

        Step step = stepBuilderFactory.get(Constants.STEP_BUILDER_FACTORY_NAME)
                .<TedTalk, TedTalk>chunk(Constants.FILE_CHUNK_SIZE)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get(Constants.JOB_BUILDER_FACTORY_NAME)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<TedTalk> itemReader(@Value("#{jobParameters[filePath]}") String filePath) {

        FlatFileItemReader<TedTalk> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(filePath));
        flatFileItemReader.setName(Constants.FLAT_ITEM_READER_NAME);
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<TedTalk> lineMapper() {

        DefaultLineMapper<TedTalk> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(Constants.DELIMITER_COMMA);
        lineTokenizer.setStrict(Boolean.FALSE);
        lineTokenizer.setNames(Constants.FILE_HEADER_TITLE, Constants.FILE_HEADER_AUTHOR, Constants.FILE_HEADER_DATE, Constants.FILE_HEADER_VIEWS, Constants.FILE_HEADER_LIKES, Constants.FILE_HEADER_LINK);

        BeanWrapperFieldSetMapper<TedTalk> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TedTalk.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}

package io.pilot.system.batch;

import io.pilot.system.model.TedTalk;
import io.pilot.system.repository.TedTalkRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchWriter implements ItemWriter<TedTalk> {

    @Autowired
    private TedTalkRepository tedTalkRepository;

    @Override
    public void write(List<? extends TedTalk> tedTalks){
        tedTalkRepository.saveAll(tedTalks);
    }
}

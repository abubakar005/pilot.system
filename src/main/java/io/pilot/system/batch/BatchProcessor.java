package io.pilot.system.batch;

import io.pilot.system.model.TedTalk;
import io.pilot.system.util.Constants;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BatchProcessor implements ItemProcessor<TedTalk, TedTalk> {

    @Override
    public TedTalk process(TedTalk tedTalk) {
        tedTalk.setCreationDate(LocalDateTime.now());
        tedTalk.setCreatedBy(Constants.SYSTEM_USER);
        return tedTalk;
    }
}

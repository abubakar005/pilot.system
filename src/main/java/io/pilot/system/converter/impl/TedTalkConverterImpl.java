package io.pilot.system.converter.impl;

import io.pilot.system.converter.TedTalkConverter;
import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.model.TedTalk;
import io.pilot.system.util.Constants;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TedTalkConverterImpl implements TedTalkConverter {


    @Override
    public TedTalk createTedTalkRequestDtoToTedTalk(CreateTedTalkRequestDto request) {
        TedTalk tedTalk = new TedTalk();
        tedTalk.setTitle(request.getTitle());
        tedTalk.setAuthor(request.getAuthor());
        tedTalk.setDate(request.getDate());
        tedTalk.setViews(request.getViews());
        tedTalk.setLikes(request.getLikes());
        tedTalk.setLink(request.getLink());
        tedTalk.setCreatedBy(Constants.SYSTEM_USER);
        tedTalk.setCreationDate(LocalDateTime.now());

        return tedTalk;
    }

    @Override
    public TedTalk updateTedTalkRequestDtoToTedTalk(UpdateTedTalkRequestDto updateRequest, TedTalk tedTalk) {
        tedTalk.setTitle(updateRequest.getTitle());
        tedTalk.setAuthor(updateRequest.getAuthor());
        tedTalk.setDate(updateRequest.getDate());
        tedTalk.setViews(updateRequest.getViews());
        tedTalk.setLikes(updateRequest.getLikes());
        tedTalk.setLink(updateRequest.getLink());
        tedTalk.setUpdatedBy(Constants.SYSTEM_USER);
        tedTalk.setUpdatedDate(LocalDateTime.now());

        return tedTalk;
    }
}

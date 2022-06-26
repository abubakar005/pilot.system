package io.pilot.system.converter;

import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.model.TedTalk;

public interface TedTalkConverter {

    TedTalk createTedTalkRequestDtoToTedTalk(CreateTedTalkRequestDto request);
    TedTalk updateTedTalkRequestDtoToTedTalk(UpdateTedTalkRequestDto request, TedTalk tedTalk);
}

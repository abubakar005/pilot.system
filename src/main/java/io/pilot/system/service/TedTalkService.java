package io.pilot.system.service;

import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.TedTalkSearchCriteriaDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.model.TedTalk;

import java.util.List;

public interface TedTalkService {

    TedTalk createTedTalk(CreateTedTalkRequestDto request);
    TedTalk updateTedTalk(UpdateTedTalkRequestDto request);
    List<TedTalk> findAll(TedTalkSearchCriteriaDto searchCriteria);
    TedTalk getTedTalkById(long id);
    void deleteTedTalkById(long id);
}

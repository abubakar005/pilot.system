package io.pilot.system.service.impl;

import io.pilot.system.converter.TedTalkConverter;
import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.TedTalkSearchCriteriaDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.enums.Error;
import io.pilot.system.exception.BadRequestException;
import io.pilot.system.exception.ElementNotFoundException;
import io.pilot.system.model.TedTalk;
import io.pilot.system.repository.TedTalkRepository;
import io.pilot.system.service.TedTalkService;
import io.pilot.system.specification.TedTalkSpecification;
import io.pilot.system.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TedTalkServiceImpl implements TedTalkService {

    @Autowired
    private TedTalkRepository tedTalkRepository;

    @Autowired
    private TedTalkConverter tedTalkConverter;

    @Autowired
    private TedTalkSpecification tedTalkSpecification;


    @Override
    public TedTalk createTedTalk(CreateTedTalkRequestDto request) {

        ValidationUtil.validateRequest(request);
        checkDuplicateTitleOrLink(0l, request.getTitle(), request.getLink());
        TedTalk tedTalk = tedTalkConverter.createTedTalkRequestDtoToTedTalk(request);
        tedTalkRepository.save(tedTalk);

        return tedTalk;
    }

    @Override
    public TedTalk updateTedTalk(UpdateTedTalkRequestDto request) {

        TedTalk tedTalk = getTedTalkById(request.getId());
        ValidationUtil.validateRequest(request);
        checkDuplicateTitleOrLink(request.getId(), request.getTitle(), request.getLink());

        tedTalk = tedTalkConverter.updateTedTalkRequestDtoToTedTalk(request, tedTalk);
        tedTalkRepository.save(tedTalk);

        return tedTalk;
    }

    @Override
    public List<TedTalk> findAll(TedTalkSearchCriteriaDto searchCriteria) {
        return tedTalkRepository.findAll(tedTalkSpecification.getTedTalks(searchCriteria));
    }

    @Override
    public TedTalk getTedTalkById(long id) {
System.out.println("--------------------------");
        Optional<TedTalk> tedTalk = tedTalkRepository.findById(id);

        if(!tedTalk.isPresent())
            throw new ElementNotFoundException(Error.TED_TALK_NOT_FOUND.getCode(), String.format(Error.TED_TALK_NOT_FOUND.getMsg(), id));

        return tedTalk.get();
    }

    @Override
    public void deleteTedTalkById(long id) {
        // Checking if exists with the input Id
        getTedTalkById(id);
        tedTalkRepository.deleteById(id);
    }

    private void checkDuplicateTitleOrLink(Long id, String title, String link) {

        List<TedTalk> tedTalks = tedTalkRepository.findByTitleOrLink(title, link);

        tedTalks.forEach(tedTalk -> {
            if(id == 0 || !tedTalk.getId().equals(id)) {
                if(title.equals(tedTalk.getTitle()))
                    throw new BadRequestException(Error.REQUEST_BODY_CONTAINS_DUPLICATE_TITLE.getCode(), Error.REQUEST_BODY_CONTAINS_DUPLICATE_TITLE.getMsg());

                throw new BadRequestException(Error.REQUEST_BODY_CONTAINS_DUPLICATE_LINK.getCode(), Error.REQUEST_BODY_CONTAINS_DUPLICATE_LINK.getMsg());
            }
        });
    }
}

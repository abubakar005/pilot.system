package io.pilot.system.resource;

import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.TedTalkSearchCriteriaDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.model.TedTalk;
import io.pilot.system.service.TedTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ted-talk")
public class TedTalkController {

    @Autowired
    private TedTalkService tedTalkService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TedTalk addTedTalk(@RequestBody CreateTedTalkRequestDto requestDto) {
        return tedTalkService.createTedTalk(requestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CachePut(value = "tedTalks", key = "#requestDto.id")
    public TedTalk updateTedTalk(@RequestBody UpdateTedTalkRequestDto requestDto) {
        return tedTalkService.updateTedTalk(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TedTalk> findAllTedTalks(TedTalkSearchCriteriaDto searchCriteria) {
        return tedTalkService.findAll(searchCriteria);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "tedTalks", key = "#id")
    public TedTalk getTedTalkById(@PathVariable("id") Long id) {
        return tedTalkService.getTedTalkById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "tedTalks", key = "#id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTedTalkById(@PathVariable("id") Long id) {
        tedTalkService.deleteTedTalkById(id);
    }
}

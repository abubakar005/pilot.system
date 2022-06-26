package io.pilot.system.service.impl;

import io.pilot.system.converter.TedTalkConverter;
import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.TedTalkSearchCriteriaDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.exception.BadRequestException;
import io.pilot.system.exception.ElementNotFoundException;
import io.pilot.system.model.TedTalk;
import io.pilot.system.repository.TedTalkRepository;
import io.pilot.system.service.TedTalkService;
import io.pilot.system.specification.TedTalkSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TedTalkServiceImplTest {

    @InjectMocks
    private TedTalkService tedTalkService = new TedTalkServiceImpl();

    @Mock
    private TedTalkRepository tedTalkRepository;

    @Mock
    private TedTalkConverter tedTalkConverter;

    @Mock
    private TedTalkSpecification tedTalkSpecification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void createTedTalk() {
        CreateTedTalkRequestDto requestDto = getMockedCreateTedTalkRequestDto();

        when(tedTalkRepository.findByTitleOrLink(anyString(), anyString())).thenReturn(Arrays.asList());
        when(tedTalkConverter.createTedTalkRequestDtoToTedTalk(any())).thenReturn(getMockedTedTalk());
        when(tedTalkRepository.save(any())).thenReturn(getMockedTedTalk());

        TedTalk response = tedTalkService.createTedTalk(requestDto);

        assertNotNull(response);
        assertEquals("Test TedTalk", response.getTitle());
        assertEquals("Test Author", response.getAuthor());
        assertEquals(1, response.getId());
    }

    @Test
    void createTedTalkWithDuplicateTitle() {
        CreateTedTalkRequestDto requestDto = getMockedCreateTedTalkRequestDto();

        when(tedTalkRepository.findByTitleOrLink(anyString(), anyString())).thenReturn(getMockedTedTalkList());
        when(tedTalkConverter.createTedTalkRequestDtoToTedTalk(any())).thenReturn(getMockedTedTalk());
        when(tedTalkRepository.save(any())).thenReturn(getMockedTedTalk());

        try {
            tedTalkService.createTedTalk(requestDto);
        } catch (BadRequestException ex) {
            assertEquals("Title can not be duplicate!", ex.getMsg());
        }
    }

    @Test
    void createTedTalkWithDuplicateLink() {
        CreateTedTalkRequestDto requestDto = getMockedCreateTedTalkRequestDto();

        when(tedTalkRepository.findByTitleOrLink(anyString(), anyString())).thenReturn(Arrays.asList(getMockedTedTalkWithDuplicateLink()));
        when(tedTalkConverter.createTedTalkRequestDtoToTedTalk(any())).thenReturn(getMockedTedTalk());
        when(tedTalkRepository.save(any())).thenReturn(getMockedTedTalk());

        try {
            tedTalkService.createTedTalk(requestDto);
        } catch (BadRequestException ex) {
            assertEquals("Link can not be duplicate!", ex.getMsg());
        }
    }

    @Test
    void updateTedTalk() {
        UpdateTedTalkRequestDto requestDto = getMockedUpdateTedTalkRequestDto();
        TedTalk tedTalk = getMockedTedTalk();
        TedTalk updatedTedTalk = getMockedUpdatedTedTalk();

        when(tedTalkRepository.findById(anyLong())).thenReturn(Optional.of(tedTalk));
        when(tedTalkRepository.findByTitleOrLink(anyString(), anyString())).thenReturn(getMockedTedTalkList());
        when(tedTalkConverter.updateTedTalkRequestDtoToTedTalk(any(), any())).thenReturn(updatedTedTalk);
        when(tedTalkRepository.save(any())).thenReturn(updatedTedTalk);

        TedTalk response = tedTalkService.updateTedTalk(requestDto);

        assertNotNull(response);
        assertEquals("Test TedTalk Update", response.getTitle());
        assertEquals("Test Author", response.getAuthor());
        assertEquals(1, response.getId());
    }

    @Test
    void updateTedTalkWithIdNotFound() {
        UpdateTedTalkRequestDto requestDto = getMockedUpdateTedTalkRequestDto();
        TedTalk updatedTedTalk = getMockedUpdatedTedTalk();

        when(tedTalkRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(tedTalkRepository.findByTitleOrLink(anyString(), anyString())).thenReturn(getMockedTedTalkList());
        when(tedTalkConverter.updateTedTalkRequestDtoToTedTalk(any(), any())).thenReturn(updatedTedTalk);
        when(tedTalkRepository.save(any())).thenReturn(updatedTedTalk);

        try {
            tedTalkService.updateTedTalk(requestDto);
        } catch (ElementNotFoundException ex) {
            assertEquals("TedTalk not found against the Id "+requestDto.getId(), ex.getMsg());
        }
    }

    @Test
    void findAll() {
        when(tedTalkSpecification.getTedTalks(any())).thenReturn(getMockedSpecification());
        when(tedTalkRepository.findAll(any(Specification.class))).thenReturn(getMockedTedTalkList());

        TedTalkSearchCriteriaDto request = new TedTalkSearchCriteriaDto();
        List<TedTalk> response = tedTalkService.findAll(request);

        assertNotNull(response);
        assertEquals(1, response.get(0).getId());
        assertEquals("Test TedTalk", response.get(0).getTitle());
        assertEquals(1, response.size());
    }

    @Test
    void findAllWithParam() {
        when(tedTalkSpecification.getTedTalks(any())).thenReturn(getMockedSpecification());
        when(tedTalkRepository.findAll(any(Specification.class))).thenReturn(getMockedTedTalkList());

        TedTalkSearchCriteriaDto request = new TedTalkSearchCriteriaDto();
        request.setTitle("Test TedTalk");

        List<TedTalk> response = tedTalkService.findAll(request);

        assertNotNull(response);
        assertEquals(1, response.get(0).getId());
        assertEquals("Test TedTalk", response.get(0).getTitle());
        assertEquals(1, response.size());
    }

    @Test
    void getTedTalkById() {
        when(tedTalkRepository.findById(anyLong())).thenReturn(Optional.of(getMockedTedTalk()));

        TedTalk response = tedTalkService.getTedTalkById(1l);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Test TedTalk", response.getTitle());
    }

    @Test
    void deleteTedTalkById() {
        when(tedTalkRepository.findById(anyLong())).thenReturn(Optional.of(getMockedTedTalk()));
        tedTalkService.deleteTedTalkById(100l);
        verify(tedTalkRepository, times(1)).deleteById(100l);
    }

    private CreateTedTalkRequestDto getMockedCreateTedTalkRequestDto() {
        return CreateTedTalkRequestDto
                .builder()
                .title("Test TedTalk")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();
    }

    private UpdateTedTalkRequestDto getMockedUpdateTedTalkRequestDto() {
        return UpdateTedTalkRequestDto
                .builder()
                .id(1l)
                .title("Test TedTalk Update")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();
    }

    private List<TedTalk> getMockedTedTalkList() {
        return Arrays.asList(getMockedTedTalk());
    }

    private TedTalk getMockedTedTalk() {
        return TedTalk
                .builder()
                .id(1l)
                .title("Test TedTalk")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();
    }

    private TedTalk getMockedTedTalkWithDuplicateLink() {
        return TedTalk
                .builder()
                .id(1l)
                .title("Test TedTalk 1")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();
    }

    private TedTalk getMockedUpdatedTedTalk() {
        return TedTalk
                .builder()
                .id(1l)
                .title("Test TedTalk Update")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();
    }

    private Specification<TedTalk> getMockedSpecification() {
        return (Specification<TedTalk>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
package io.pilot.system.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.model.TedTalk;
import io.pilot.system.service.TedTalkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TedTalkController.class)
@AutoConfigureMockMvc(addFilters = false)
class TedTalkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TedTalkService tedTalkService;

    public String BASE_URL = "/api/v1/ted-talk";


    @Test
    void addTedTalk() throws Exception {

        TedTalk tedTalk = new TedTalk().toBuilder().build();

        CreateTedTalkRequestDto newTedTalk = CreateTedTalkRequestDto
                .builder()
                .title("Test TedTalk")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();

        when(tedTalkService.createTedTalk(any()))
                .thenReturn(tedTalk);

        RequestBuilder request = MockMvcRequestBuilders
                .post(BASE_URL)
                .content(asJsonString(newTedTalk))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void updateTedTalk() throws Exception {

        TedTalk tedTalk = new TedTalk().toBuilder().build();

        UpdateTedTalkRequestDto updateTedTalk = UpdateTedTalkRequestDto
                .builder()
                .id(1l)
                .title("Test TedTalk Update")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();

        when(tedTalkService.createTedTalk(any()))
                .thenReturn(tedTalk);

        RequestBuilder request = MockMvcRequestBuilders
                .put(BASE_URL)
                .content(asJsonString(updateTedTalk))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findAllTedTalks() throws Exception {

        TedTalk tedTalk = new TedTalk().toBuilder().build();
        when(tedTalkService.findAll(any()))
                .thenReturn(Arrays.asList(tedTalk));

        RequestBuilder request = MockMvcRequestBuilders
                .get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getTedTalkById() throws Exception {

        TedTalk tedTalk = new TedTalk().toBuilder().build();
        when(tedTalkService.getTedTalkById(anyLong()))
                .thenReturn(tedTalk);

        RequestBuilder request = MockMvcRequestBuilders
                .get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteTedTalkById() throws Exception {
        doNothing().when(tedTalkService).deleteTedTalkById(anyLong());

        RequestBuilder request = MockMvcRequestBuilders
                .delete(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
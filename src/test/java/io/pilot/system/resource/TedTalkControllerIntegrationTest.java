package io.pilot.system.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.model.TedTalk;
import io.pilot.system.repository.TedTalkRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class TedTalkControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TedTalkRepository tedTalkRepository;

    public String BASE_URL = "/api/v1/ted-talk";
    static Long tedTalkId;

    @Test
    @DisplayName("1. When a new tedTalk is requested then it is persisted")
    void addTedTalk() throws Exception {
        CreateTedTalkRequestDto newTedTalk = CreateTedTalkRequestDto
                .builder()
                .title("Test TedTalk")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();

        tedTalkId =
                mapper
                        .readValue(
                                mockMvc
                                        .perform(
                                                post(BASE_URL)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        //.headers(getHeaders())
                                                        .content(mapper.writeValueAsString(newTedTalk)))
                                        .andExpect(status().isCreated())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                                TedTalk.class)
                        .getId();

        TedTalk tedTalk = tedTalkRepository
                .findById(tedTalkId)
                .orElseThrow(
                        () -> new IllegalStateException("New tedTalk has not been saved in the repository"));
        assertThat(tedTalk.getTitle(),
                equalTo(newTedTalk.getTitle()));
    }

    @Test
    @DisplayName("2. When a update tedTalk is requested then it is updated")
    void updateTedTalk() throws Exception {
        UpdateTedTalkRequestDto updateTedTalk = UpdateTedTalkRequestDto
                .builder()
                .id(tedTalkId)
                .title("Test TedTalk Update")
                .author("Test Author")
                .date("June 2022")
                .views(1l)
                .likes(1l)
                .link("https://ted.com/talks/test")
                .build();

        tedTalkId =
                mapper
                        .readValue(
                                mockMvc
                                        .perform(
                                                put(BASE_URL)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(mapper.writeValueAsString(updateTedTalk)))
                                        .andExpect(status().isOk())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                                TedTalk.class)
                        .getId();

        TedTalk tedTalk = tedTalkRepository
                .findById(tedTalkId)
                .orElseThrow(
                        () -> new IllegalStateException("TedTalk has not been updated in the repository"));

        assertThat(tedTalk.getTitle(),
                equalTo(updateTedTalk.getTitle()));
    }

    @Test
    @DisplayName("3. When all TedTalks are requested")
    void findAllTedTalks() throws Exception {
        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize((int) tedTalkRepository.count())));
    }

    @Test
    @DisplayName("4. When a TedTalk is requested by Id")
    void getTedTalkById() throws Exception {
        mockMvc
                .perform(get(BASE_URL+"/"+tedTalkId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("title", equalTo("Test TedTalk Update")));
    }

    @Test
    @DisplayName("5. When a TedTalk delete is requested by Id")
    void deleteTedTalkById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL+"/"+tedTalkId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    HttpHeaders getHeaders(){
        return new HttpHeaders() {{
            String auth = "admin" + ":" + "admin";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
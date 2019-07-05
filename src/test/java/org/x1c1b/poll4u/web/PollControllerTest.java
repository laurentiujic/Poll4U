package org.x1c1b.poll4u.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.x1c1b.poll4u.JsonUtils;
import org.x1c1b.poll4u.WithMockUserDetails;
import org.x1c1b.poll4u.dto.ChoiceCreationDTO;
import org.x1c1b.poll4u.dto.ChoiceDTO;
import org.x1c1b.poll4u.dto.PollCreationDTO;
import org.x1c1b.poll4u.dto.PollDTO;
import org.x1c1b.poll4u.security.UserPrincipalService;
import org.x1c1b.poll4u.service.PollService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PollController.class)
public class PollControllerTest {

    @Autowired private MockMvc mvc;
    @MockBean private PollService pollService;
    @MockBean private UserPrincipalService principalService;

    private PollDTO expected;

    @Before
    public void setUp() {

        ChoiceDTO firstChoice = new ChoiceDTO();
        firstChoice.setId(1L);
        firstChoice.setDescription("Green");

        ChoiceDTO secondChoice = new ChoiceDTO();
        secondChoice.setId(1L);
        secondChoice.setDescription("Green");

        expected = new PollDTO();
        expected.setId(1L);
        expected.setQuestion("What's your favorite color?");
        expected.setExpiration(new Date());
        expected.setCreatedAt(new Date());
        expected.setCreatedAt(new Date());
        expected.setUpdatedBy(1L);
        expected.setCreatedBy(1L);
        expected.setChoices(Arrays.asList(firstChoice, secondChoice));
    }

    @Test public void findAll() throws Exception {

        List<PollDTO> polls = Collections.singletonList(expected);
        Page<PollDTO> page = new PageImpl<>(polls);

        given(pollService.findAll(any())).willReturn(page);

        mvc.perform(get("/api/v1/polls")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is(expected.getId().intValue())))
                .andExpect(jsonPath("$.content[0].question", is(expected.getQuestion())))
                .andExpect(jsonPath("$.content[0].choices").isArray())
                .andExpect(jsonPath("$.content[0].choices").isNotEmpty());
    }

    @Test
    @WithMockUserDetails
    public void create() throws Exception {

        PollCreationDTO creation = new PollCreationDTO();
        creation.setQuestion("What's your favorite color?");
        creation.setExpiration(new Date());
        creation.setChoices(Arrays.asList(new ChoiceCreationDTO("Green"),
                new ChoiceCreationDTO("Red")));

        given(pollService.create(any())).willReturn(expected);

        mvc.perform(post("/api/v1/polls")
                .content(JsonUtils.toJson(creation))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(expected.getId().intValue())))
                .andExpect(jsonPath("$.question", is(expected.getQuestion())))
                .andExpect(jsonPath("$.choices").isArray())
                .andExpect(jsonPath("$.choices").isNotEmpty());
    }
}
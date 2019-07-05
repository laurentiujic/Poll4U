package org.x1c1b.poll4u.web;

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
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.dto.UserUpdateDTO;
import org.x1c1b.poll4u.security.UserPrincipalService;
import org.x1c1b.poll4u.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired private MockMvc mvc;
    @MockBean private UserService userService;
    @MockBean private UserPrincipalService principalService;

    @Test public void findAll() throws Exception {

        UserDTO user = new UserDTO(1L, "user","user@web.de", "Abc123");
        List<UserDTO> users = Collections.singletonList(user);
        Page<UserDTO> page = new PageImpl<>(users);

        given(userService.findAll(any())).willReturn(page);

        mvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.content[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$.content[0].email", is(user.getEmail())));
    }

    @Test public void findById() throws Exception {

        UserDTO user = new UserDTO(1L, "user","user@web.de", "Abc123");

        given(userService.findById(1L)).willReturn(user);

        mvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test public void create() throws Exception {

        UserDTO user = new UserDTO(1L, "user","user@web.de", "Abc123");
        RegistrationDTO registration = new RegistrationDTO("user","user@web.de", "Abc123");

        given(userService.create(any())).willReturn(user);

        mvc.perform(post("/api/v1/users")
                .content(JsonUtils.toJson(registration))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    @WithMockUserDetails
    public void updateById() throws Exception {

        UserDTO user = new UserDTO(1L, "user","user@web.de", "Abc123");
        UserUpdateDTO update = new UserUpdateDTO(1L, "user","user@web.de", "Abc123");

        given(userService.updateById(eq(1L), any())).willReturn(user);

        mvc.perform(put("/api/v1/users/1")
                .content(JsonUtils.toJson(update))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    @WithMockUserDetails
    public void deleteById() throws Exception {

        willDoNothing().given(userService).deleteById(any());

        mvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent());
    }
}
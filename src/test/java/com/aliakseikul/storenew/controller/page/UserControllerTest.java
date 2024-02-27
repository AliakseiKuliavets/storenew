package com.aliakseikul.storenew.controller.page;


import com.aliakseikul.storenew.dto.UserCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String USER_ID_VALID = "a197d1bb-8990-4b08-ad8a-9ec55718fcb8";

    private final static UserCreateDto USER_CREATE_DTO = new UserCreateDto(
            "TestUserNickName",
            "TestUserPassword",
            "TestUserFirstName",
            "TestUserLastName",
            "TestUserEmail@gmail.com"
    );

    //-----------------------------getUserById()------------------------------------
    @WithMockUser("/some")
    @Test
    void getUserByIdPositiveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/id")
                        .param("id", USER_ID_VALID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //-----------------------------addUser()------------------------------------

    @Test
    void addUserPositiveTest() throws Exception {
        String requestBody = objectMapper.writeValueAsString(USER_CREATE_DTO);

        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }
}

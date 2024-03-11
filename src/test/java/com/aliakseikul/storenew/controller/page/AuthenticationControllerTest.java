package com.aliakseikul.storenew.controller.page;


import com.aliakseikul.storenew.dto.auth.AuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //-----------------------------authenticate()------------------------------------
    @WithMockUser("/some")
    @Test
    void authenticateTest200() throws Exception {
        AuthenticationRequest user = new AuthenticationRequest(
                "admin",
                "admin"
        );
        String requestBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authenticate")
                        .content(requestBody)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "admin, 1234",
            "1234, admin",
            "1234, 1234"
    })
    void authenticateTest500(String userNickName, String userPassword) throws Exception {
        AuthenticationRequest user = new AuthenticationRequest(
                userNickName,
                userPassword
        );
        String requestBody = objectMapper.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/authenticate")
                        .content(requestBody)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}

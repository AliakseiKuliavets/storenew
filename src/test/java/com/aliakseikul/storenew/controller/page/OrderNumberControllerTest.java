package com.aliakseikul.storenew.controller.page;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class OrderNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;


    //-----------------------------getOrderNumberById()------------------------------------
    @WithMockUser("/some")
    @Test
    void getOrderNumberByIdTest200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/")
                        .param("id", "770d42f7-4765-4fab-88aa-86dad34995eb")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getOrderNumberByIdTest403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/")
                        .param("id", "770d42f7-4765-4fab-88aa-86dad34995eb")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser("/some")
    @Test
    void getOrderNumberByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/")
                        .param("id", "770d42f7-4765-4fab-88aa-86dad34995e2")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "770d42f7-4765-4fab-88aa-86dad3499",
            "770d42f7-4765-4fab-88aa-86dad34995e22222"
    })
    void getOrderNumberByIdTest500(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/")
                        .param("id", id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------getOrderByUserRecipientId()------------------------------------
    @WithMockUser("/some")
    @Test
    void getOrderByUserRecipientIdTest200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/idRecipient/")
                        .param("id", "a197d1bb-8990-4b08-ad8a-9ec55718fcb8")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getOrderByUserRecipientIdTest403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/idRecipient/")
                        .param("id", "a197d1bb-8990-4b08-ad8a-9ec55718fcb8")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "770d42f7-4765-4fab-88aa-86dad3499",
            "770d42f7-4765-4fab-88aa-86dad34995e22222"
    })
    void getOrderByUserRecipientIdTest500(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/idRecipient/")
                        .param("id", id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}

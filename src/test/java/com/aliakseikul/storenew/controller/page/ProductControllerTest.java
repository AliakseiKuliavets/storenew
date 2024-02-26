package com.aliakseikul.storenew.controller.page;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
@Rollback
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getProductById() throws Exception {
        String productId = "35026fc0-dbfc-4d52-9c1c-a203929ea63d";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/id")
                        .param("id", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(productId)));
    }

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateProductNameWithId() throws Exception {
        String productId = "35026fc0-dbfc-4d52-9c1c-a203929ea63d";
        String productName = "some";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/name/" + productId + "and" + productName)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("")));
    }
}
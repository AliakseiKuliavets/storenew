package com.aliakseikul.storenew.controller.page;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsString;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0")
//            .withInitScript("/drop.sql")
            .withInitScript("create.sql")
            .withInitScript("insert.sql");

    @BeforeAll
    static void setUp(){
        mySQLContainer.start();
    }

    @AfterAll
    static void tearDwn(){
        mySQLContainer.stop();
    }

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
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(productName)));
    }
}
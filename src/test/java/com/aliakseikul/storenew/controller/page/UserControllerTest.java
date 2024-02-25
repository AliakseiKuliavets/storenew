package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.UserRole;
import com.aliakseikul.storenew.mapper.UserMapper;
import com.aliakseikul.storenew.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.1.0");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public static String asJsonString(final Object object) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(object);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addUser() throws Exception {
        UserCreateDto user = UserCreateDto.builder()
                .userNickname("SomeTest")
                .userEmail("TestEmail")
                .userFirstName("TestName")
                .userLastName("TestLastName")
                .userPassword("TestPassword")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user/add")
                        .contentType("application/json")
                        .content(asJsonString(user))
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}

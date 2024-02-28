package com.aliakseikul.storenew.controller.page;


import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.UserRole;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

    private static final String USER_ID_VALID = "a197d1bb-8990-4b08-ad8a-9ec55718fcb8";

    private static final String USER_ID_VALID_NOT_VALID = "a197d1bb-8990-4b08-ad8a-9ec55718fcb2";

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

    @Test
    void getProductByIdTest403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/id")
                        .param("id", USER_ID_VALID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser("/some")
    @Test
    void getProductByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/id")
                        .param("id", USER_ID_VALID_NOT_VALID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "35026fc0-dbfc-4d52-9c1c-a203929ea63",
            "35026fc0-dbfc-4d52-9c1c-a203929ea63E2",
    })
    void getProductByIdTest500(String productId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/id")
                        .param("id", productId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
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

    //-----------------------------changeUserRoleById()------------------------------------
    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "UsEr",
            "USER",
            "user"
    })
    void changeUserRoleById(String userRole) throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.put("/api/user/change/role")
                                .param("userId", USER_ID_VALID)
                                .param("role", userRole)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(UserRole.USER, getById().getUserRole());
    }

    @Test
    void changeUserRoleById403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/change/role")
                        .param("userId", USER_ID_VALID)
                        .param("role", String.valueOf(UserRole.USER))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "a197d1bb-8990-4b08-ad8a-9ec55718fcb2, USER",
            "a197d1bb-8990-4b08-ad8a-9ec55718fcb2, user",
            "a197d1bb-8990-4b08-ad8a-9ec55718fcb2, UsEr",
            "a197d1bb-8990-4b08-ad8a-9ec55718fcb8, UEr",
            "a197d1bb-8990-4b08-ad8a-9ec55718fcb8, Usser",

    })
    void getAllProductsByCategoryTest404(String userId, String userRole) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/change/role")
                        .param("userId", userId)
                        .param("role", userRole)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "a197d1bb-8990-4b08-ad8a-9ec55718f, USER",
            "a197d1bb-8990-4b08-ad8a-9ec55718fcb22, user",
            "a197d1bb-8990-4b08-ad8a-9ec55718fcb8, 12345678901234567890123456789012345678901234567"
    })
    void getAllProductsByCategoryTest500(String userId, String userRole) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/change/role")
                        .param("userId", userId)
                        .param("role", userRole)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------deleteUserById()------------------------------------
    @WithMockUser("/some")
    @Test
    void deleteUserById() throws Exception {

        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/remove/" + USER_ID_VALID)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void deleteUserById403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/remove/" + USER_ID_VALID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser("/some")
    @Test
    void deleteUserById404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/remove/" + USER_ID_VALID_NOT_VALID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "35026fc0-dbfc-4d52-9c1c-a203929ea63",
            "35026fc0-dbfc-4d52-9c1c-a203929ea63E2",
    })
    void deleteUserById500(String id) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/remove/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @WithMockUser("/some")
    private User getById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/id")
                        .param("id", USER_ID_VALID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String userJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(userJson, User.class);
    }
}

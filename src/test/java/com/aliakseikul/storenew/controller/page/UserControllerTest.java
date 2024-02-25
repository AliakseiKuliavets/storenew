package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.mapper.UserMapper;
import com.aliakseikul.storenew.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/create.sql")
@Sql("/insert.sql")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Test
    void getUserById() throws Exception {
        UUID uuid = UUID.fromString("a197d1bb-8990-4b08-ad8a-9ec55718fcb8");
        User user = userRepository.findById(uuid).orElse(null);
        UserDto userDto = userMapper.toDto(user);

        var result = mockMvc.perform(get("/api/user")
                        .param("id", "a197d1bb-8990-4b08-ad8a-9ec55718fcb8")
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(new ObjectMapper().writeValueAsString(userDto));
    }
}

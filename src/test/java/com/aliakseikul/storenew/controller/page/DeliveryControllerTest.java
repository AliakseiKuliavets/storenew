package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.DeliveryDto;
import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import com.aliakseikul.storenew.entity.enums.StatusTracking;
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
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String VALID_DELIVERY_ID = "1fac8484-5093-4532-b6d8-d632257c84cc";
    private static final String NOT_VALID_DELIVERY_ID = "1fac8484-5093-4532-b6d8-d632257c84c2";

    //-----------------------------getDeliveryById()------------------------------------
    @WithMockUser("/some")
    @Test
    void getDeliveryByIdTest200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery/")
                        .param("id", VALID_DELIVERY_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getProductByIdTest403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery/")
                        .param("id", VALID_DELIVERY_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser("/some")
    @Test
    void getProductByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery/")
                        .param("id", NOT_VALID_DELIVERY_ID)
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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery/")
                        .param("id", productId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------addDelivery()------------------------------------
    @WithMockUser("/some")
    @Test
    void addUserPositiveTest() throws Exception {
        DeliveryDto deliveryDto = new DeliveryDto(
                "TestAddress",
                PaymentMethod.CASH,
                StatusTracking.DELIVERED
        );
        String requestBody = objectMapper.writeValueAsString(deliveryDto);

        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/delivery/add")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    //-----------------------------changeAddressById()------------------------------------
    @WithMockUser("/some")
    @Test
    void changeAddressByIdTest200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/change/")
                        .param("deliveryId", VALID_DELIVERY_ID)
                        .param("deliveryAddress", "Test Address")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void changeAddressByIdTest403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/change/")
                        .param("deliveryId", VALID_DELIVERY_ID)
                        .param("deliveryAddress", "Test Address")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser("/some")
    @Test
    void changeAddressByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/change/")
                        .param("deliveryId", NOT_VALID_DELIVERY_ID)
                        .param("deliveryAddress", "Test Address")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser("/some")
    @ParameterizedTest
    @CsvSource(value = {
            "1fac8484-5093-4532-b6d8-d632257c84c",
            "1fac8484-5093-4532-b6d8-d632257c84cc2"
    })
    void changeAddressByIdTest500(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/change/")
                        .param("deliveryId", id)
                        .param("deliveryAddress", "Test Address")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------deleteDeliveryById()------------------------------------
    @WithMockUser("/some")
    @Test
    void deleteDeliveryByIdByIdTest200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delivery/delete/")
                        .param("deliveryId", VALID_DELIVERY_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteDeliveryByIdTest403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delivery/delete/")
                        .param("deliveryId", VALID_DELIVERY_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser("/some")
    @Test
    void deleteDeliveryByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delivery/delete/")
                        .param("deliveryId", NOT_VALID_DELIVERY_ID)
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
    void deleteDeliveryByIdTest500(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delivery/delete/")
                        .param("deliveryId", id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}

package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EnumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProductCategories() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/enum/allCategory"))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allCategory = mvcResult.getResponse().getContentAsString();
        List<ProductCategory> receivedCategoryAll = objectMapper.readValue(allCategory,
                new TypeReference<>() {
                });
        Assertions.assertEquals(3, receivedCategoryAll.size());
    }

    @Test
    void getProductBrand() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/enum/allBrand"))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allBrand = mvcResult.getResponse().getContentAsString();
        List<ProductBrand> receivedBrandAll = objectMapper.readValue(allBrand,
                new TypeReference<>() {
                });
        Assertions.assertEquals(10, receivedBrandAll.size());
    }
}
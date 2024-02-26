package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/drop.sql")
@Sql("/create.sql")
@Sql("/insert.sql")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getProductByName() throws Exception {
        String name = "Iphone 8";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/name")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAllByName = objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, receivedProductsAllByName.size());
    }

    @Test
    void getAllProducts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/all"))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAll = objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, receivedProductsAll.size());
    }

    @Test
    void getAllProductsByCategory() throws Exception {
        String category = "ELECTRONICS";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByCategory/")
                        .param("category", category)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAllByCategory = objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, receivedProductsAllByCategory.size());
    }

    @Test
    void getAllProductsByBrand() throws Exception {
        String brand = "APPLE";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByBrand/")
                        .param("brand", brand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAllByBrand = objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, receivedProductsAllByBrand.size());
    }

    @Test
    void searchProductsByPriceRange() throws Exception {
        BigDecimal minPrice = BigDecimal.valueOf(1L);
        BigDecimal maxPrice = BigDecimal.valueOf(2000L);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByPrice/search")
                        .param("minPrice", minPrice.toString())
                        .param("maxPrice", maxPrice.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAllByPrice = objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, receivedProductsAllByPrice.size());
    }

    @Test
    void searchProductsByCategoryBrand() throws Exception {
        String category = "ELECTRONICS";
        String brand = "APPLE";

        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByCategoryBrand/search")
                                .param("category", category)
                                .param("brand", brand)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAllByBrandCategory = objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, receivedProductsAllByBrandCategory.size());
    }

    @Test
    void createProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("TestName");
        productDto.setProductDescription("TestDescription");
        productDto.setProductPrice(BigDecimal.valueOf(123L));
        productDto.setProductCategory(ProductCategory.OTHER);
        productDto.setProductBrand(ProductBrand.APPLE);
        productDto.setUserNickname("Bogdan");

        String requestBody = objectMapper.writeValueAsString(productDto);

        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
                                .content(requestBody)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(2, getAllProductsList().size());
    }

    @Test
    void updateProductNameWithId() throws Exception {
        String productId = "35026fc0-dbfc-4d52-9c1c-a203929ea63d";
        String productName = "some";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/name/" + productId + "and" + productName)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals("some", getById().getProductName());
    }

    private List<ProductDto> getAllProductsList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/all"))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
    }

    private Product getById() throws Exception {
        String productId = "35026fc0-dbfc-4d52-9c1c-a203929ea63d";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/id")
                        .param("id", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String productJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(productJson, Product.class);
    }
}
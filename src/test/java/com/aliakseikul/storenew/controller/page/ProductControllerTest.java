package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    private final static String PRODUCT_ID_VALID = "35026fc0-dbfc-4d52-9c1c-a203929ea63d";
    private final static String PRODUCT_ID_NOT_VALID = "35026fc0-dbfc-4d52-9c1c-a203929ea63E";
    private final static String MORE_44_CHARS = "123456789012345678901234567890123456789012345";
    private final static String PRODUCT_CATEGORY_VALID = "ELECTRONICS";
    private final static String PRODUCT_BRAND_VALID = "APPLE";

    private final static ProductDto PRODUCT_DTO_VALID = new ProductDto();

    @BeforeEach
    void setUp() {
        PRODUCT_DTO_VALID.setProductName("TestName");
        PRODUCT_DTO_VALID.setProductDescription("TestDescription");
        PRODUCT_DTO_VALID.setProductPrice(BigDecimal.valueOf(123L));
        PRODUCT_DTO_VALID.setProductCategory(ProductCategory.OTHER);
        PRODUCT_DTO_VALID.setProductBrand(ProductBrand.APPLE);
        PRODUCT_DTO_VALID.setUserNickname("Bogdan");
    }

    //-----------------------------getProductById()------------------------------------
    @Test
    void getProductByIdPositiveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/id")
                        .param("id", PRODUCT_ID_VALID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(PRODUCT_ID_VALID)));
    }

    @Test
    void getProductByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/id")
                        .param("id", PRODUCT_ID_NOT_VALID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "35026fc0-dbfc-4d52-9c1c-a203929ea63",
            "35026fc0-dbfc-4d52-9c1c-a203929ea63E2",
    })
    void getProductByIdTest500(String productId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/id")
                        .param("id", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }


    //-----------------------------getProductByName()------------------------------------
    @Test
    void getProductByNamePositiveTest() throws Exception {
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
    void getProductByNameTest500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/name")
                        .param("name", MORE_44_CHARS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------getAllProducts()------------------------------------
    @Test
    void getAllProductsPositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/all"))
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAll = objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, receivedProductsAll.size());
    }

    //-----------------------------getAllProductsByCategory()------------------------------------
    @ParameterizedTest
    @CsvSource(value = {
            PRODUCT_CATEGORY_VALID,
            "ElEcTrOnIcS",
            "electronics",
    })
    void getAllProductsByCategoryPositiveTest(String category) throws Exception {
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

    @ParameterizedTest
    @CsvSource(value = {
            "ELECTRONIC",
            "E",
            "ELECTRONICSSS",
            "electronicss",
            "electronic",
            "e",
    })
    void getAllProductsByCategoryTest404(String category) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByCategory/")
                        .param("category", category)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAllProductsByCategoryTest500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByCategory/")
                        .param("category", MORE_44_CHARS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------getAllProductsByBrand()------------------------------------
    @ParameterizedTest
    @CsvSource(value = {
            PRODUCT_BRAND_VALID,
            "APpLe",
            "apple",
    })
    void getAllProductsByBrandPositiveTest(String brand) throws Exception {
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

    @ParameterizedTest
    @CsvSource(value = {
            "APLE",
            "aple",
            "a",
            "APPPLE",
            "APLE"
    })
    void getAllProductsByBrandTest404(String brand) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByBrand/")
                        .param("brand", brand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAllProductsByBrandTest500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByBrand/")
                        .param("brand", MORE_44_CHARS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------searchProductsByPriceRange()------------------------------------
    @Test
    void searchProductsByPriceRangePositiveTest() throws Exception {
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

    @ParameterizedTest
    @CsvSource(value = {
            "0,10",
            "10,1000000",
            "0,1000000",
            "-1, 100",
            "100,-1",
            "10,1"
    })
    void searchProductsByPriceRangeTest500(String minPrice, String maxPrice) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByPrice/search")
                        .param("minPrice", minPrice)
                        .param("maxPrice", maxPrice)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------searchProductsByCategoryBrand()------------------------------------
    @Test
    void searchProductsByCategoryBrandPositiveTest() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByCategoryBrand/search")
                                .param("category", PRODUCT_CATEGORY_VALID)
                                .param("brand", PRODUCT_BRAND_VALID)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String allProduct = mvcResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAllByBrandCategory = objectMapper.readValue(allProduct,
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, receivedProductsAllByBrandCategory.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ELECTRONICS, APLE",
            "E,APPLE",
            "ELECTRONICSSS,a",
            "electronicss,APPPLE",
            "e,APLE"
    })
    void searchProductsByCategoryBrandTest404(String category, String brand) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByCategoryBrand/search")
                        .param("category", category)
                        .param("brand", brand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "123456789012345678901234567890123456789012345, APPLE",
            "ELECTRONICS,123456789012345678901234567890123456789012345"
    })
    void searchProductsByCategoryBrandTest500(String category, String brand) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/allByCategoryBrand/search")
                        .param("category", category)
                        .param("brand", brand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------createProduct()------------------------------------
    @Test
    void createProductPositiveTest() throws Exception {
        String requestBody = objectMapper.writeValueAsString(PRODUCT_DTO_VALID);

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
    void createProductTest404() throws Exception {
        PRODUCT_DTO_VALID.setUserNickname("qwer");
        String requestBody = objectMapper.writeValueAsString(PRODUCT_DTO_VALID);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
                        .content(requestBody)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createProductNotValidNickNameTest500() throws Exception {
        PRODUCT_DTO_VALID.setUserNickname(MORE_44_CHARS);
        String requestBody = objectMapper.writeValueAsString(PRODUCT_DTO_VALID);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
                        .content(requestBody)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------updateProductNameWithId()------------------------------------
    @Test
    void updateProductNameWithIdPositiveTest() throws Exception {
        String productName = "some";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/name/" + PRODUCT_ID_VALID + "and" + productName)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals("some", getById().getProductName());
    }

    @Test
    void updateProductNameWithIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/name/" + PRODUCT_ID_NOT_VALID + "and" + "adabhd")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateProductNameWithIdTest500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/name/" + PRODUCT_ID_VALID + "and" + MORE_44_CHARS)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }


    //-----------------------------updateProductDescriptionWithId()------------------------------------
    @Test
    void updateProductDescriptionWithIdPositiveTest() throws Exception {
        String productDescription = "some";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/description/"
                                + PRODUCT_ID_VALID + "and" + productDescription)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals("some", getById().getProductDescription());
    }

    @Test
    void updateProductDescriptionWithIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/description/"
                                + PRODUCT_ID_NOT_VALID + "and" + "adabhd")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //-----------------------------updateProductPriceWithId()------------------------------------
    @Test
    void updateProductPriceWithIdPositiveTest() throws Exception {
        String productPrice = "1234";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/price/"
                                + PRODUCT_ID_VALID + "and" + productPrice)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(1234.0, Double.valueOf(String.valueOf(getById().getProductPrice())));
    }

    @Test
    void updateProductPriceWithIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/price/"
                                + PRODUCT_ID_NOT_VALID + "and" + "123415")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //-----------------------------updateProductCategoryWithId()------------------------------------
    @Test
    void updateProductCategoryWithIdPositiveTest() throws Exception {
        ProductCategory productCategory = ProductCategory.SPORTS;
        String prodCategory = String.valueOf(productCategory);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/category/"
                                + PRODUCT_ID_VALID + "and" + prodCategory)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(productCategory, getById().getProductCategory());
    }

    @Test
    void updateProductCategoryWithIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/category/"
                                + PRODUCT_ID_NOT_VALID + "and" + "akufgkayfsu")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateProductCategoryWithIdNotValidCategoryTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/category/"
                                + PRODUCT_ID_VALID + "and" + "akufgkayfsu")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateProductCategoryWithIdTest500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/category/"
                                + PRODUCT_ID_VALID + "and" + MORE_44_CHARS)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------updateProductBrandWithId()------------------------------------
    @Test
    void updateProductBrandWithIdPositiveTest() throws Exception {
        ProductBrand productBrand = ProductBrand.APPLE;
        String prodBrand = String.valueOf(productBrand);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/brand/"
                                + PRODUCT_ID_VALID + "and" + prodBrand)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(productBrand, getById().getProductBrand());
    }

    @Test
    void updateProductBrandWithIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/brand/"
                                + PRODUCT_ID_NOT_VALID + "and" + "akufgkayfsu")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateProductBrandWithIdNotValidCategoryTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/brand/"
                                + PRODUCT_ID_VALID + "and" + "akufgkayfsu")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateProductBrandWithIdTest500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/update/brand/"
                                + PRODUCT_ID_VALID + "and" + MORE_44_CHARS)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    //-----------------------------deleteById()------------------------------------
    @Test
    void deleteByIdPositiveTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/product/remove/"
                                + PRODUCT_ID_VALID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(0, getAllProductsList().size());
    }

    @Test
    void deleteByIdTest404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product/remove/"
                                + PRODUCT_ID_NOT_VALID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
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
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/id")
                        .param("id", PRODUCT_ID_VALID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String productJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(productJson, Product.class);
    }
}
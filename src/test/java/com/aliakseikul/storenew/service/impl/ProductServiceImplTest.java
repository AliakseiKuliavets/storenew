package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.aliakseikul.storenew.exeption.exeptions.ProductNotFoundException;
import com.aliakseikul.storenew.repository.ProductRepository;
import com.aliakseikul.storenew.service.entit.TestListProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private String id;
    private UUID uuid;
    private List<Product> productList;
    private Product product;

    @BeforeEach
    public void setUp() {
        id = "bebf2593-ee7f-4ad3-924c-bfab0e0c0e5e";
        uuid = UUID.fromString(id);
        productList = TestListProduct.PRODUCTS;
        product = productList.get(0);
    }

    @Test
    void findByIdValidIdTest() {
        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));

        assertEquals(product, productService.findById(id));

        verify(productRepository).findById(uuid);
    }

    @Test
    void findByIdValidIdReturnNotNullTest() {
        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));

        assertNotNull(productService.findById(id));

        verify(productRepository).findById(uuid);
    }


    @ParameterizedTest
    @CsvSource(value = {
            "35026fc0-dbfc-4d52-9c1c-a203929ea63d1231231231231231231,ProductNotFoundException",
            "35026fc0,ProductNotFoundException",
            "35026fc0-dbf-4d5-9c1-a203929ea63d,ProductNotFoundException",
            "351c-a203929ea63d,ProductNotFoundException",
    })
    void findByIdNotValidIdTest(String id, ProductNotFoundException exception) {
        assertThrows(exception.getClass(), () ->
                productService.findById(id));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,iPhone",
            "2,Smartphone",
            "1,Cycling Helmet",
            "1,Portable Power Bank",
            "0,Sm 12"
    })
    void findByNameTest(int expected, String name) {
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductName().equals(name))
                        .toList()
        );
        when(productRepository.findByName(name)).thenReturn(products);

        assertEquals(expected, productRepository.findByName(name).size());

        verify(productRepository).findByName(name);
    }

    @Test
    void findByNameReturnNotNullTest() {
        String name = product.getProductName();
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductName().equals(name))
                        .toList()
        );
        when(productRepository.findByName(name)).thenReturn(products);

        assertNotNull(productRepository.findByName(name));

        verify(productRepository).findByName(name);
    }

    @Test
    void getAllProductsLengthTest() {
        when(productRepository.getAllProducts()).thenReturn(productList);

        assertEquals(38, productRepository.getAllProducts().size());
        verify(productRepository).getAllProducts();
    }

    @Test
    void getAllProductsNotNullTest() {
        when(productRepository.getAllProducts()).thenReturn(productList);

        assertNotNull(productRepository.getAllProducts());
        verify(productRepository).getAllProducts();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,2",
            "3,3",
            "10,10",
            "15,15",
            "37,37"
    })
    void getAllProductsValidityCheckTest(int indexFromList, int index) {
        product = productList.get(indexFromList);
        when(productRepository.getAllProducts()).thenReturn(productList);

        assertEquals(product, productRepository.getAllProducts().get(index));
        verify(productRepository).getAllProducts();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "16,ELECTRONICS",
            "9,SPORTS",
            "13,OTHER"
    })
    void getAllProductsByCategoriesTest(int expected, String category) {
        ProductCategory productCategory = ProductCategory.valueOf(category);
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductCategory().equals(productCategory))
                        .toList()
        );

        when(productRepository.getAllProductsByCategory(productCategory)).thenReturn(products);

        assertEquals(expected, productRepository.getAllProductsByCategory(productCategory).size());
        verify(productRepository).getAllProductsByCategory(productCategory);
    }

    @Test
    void getAllProductsByCategoryReturnNotNullTest() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductCategory().equals(category))
                        .toList()
        );

        when(productRepository.getAllProductsByCategory(category)).thenReturn(products);

        assertNotNull(productRepository.getAllProductsByCategory(category));
        verify(productRepository).getAllProductsByCategory(category);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,APPLE",
            "5,SAMSUNG",
            "4,PHILIPS",
            "3,ASUS",
            "3,LG",
            "6,NIKE",
            "3,HASBRO",
            "2,BLACK_AND_DECKER",
            "5,ADIDAS",
            "5,DELL"
    })
    void getAllProductsByBrandTest(int expected, String brand) {
        ProductBrand productBrand = ProductBrand.valueOf(brand);
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductBrand().equals(productBrand))
                        .toList()
        );

        when(productRepository.getAllProductsByBrand(productBrand)).thenReturn(products);

        assertEquals(expected, productRepository.getAllProductsByBrand(productBrand).size());
        verify(productRepository).getAllProductsByBrand(productBrand);
    }

    @Test
    void getAllProductsByBrandNotNullTest() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductCategory().equals(category))
                        .toList()
        );

        when(productRepository.getAllProductsByCategory(category)).thenReturn(products);

        assertNotNull(productRepository.getAllProductsByCategory(category));
        verify(productRepository).getAllProductsByCategory(category);
    }
}
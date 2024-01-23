package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.aliakseikul.storenew.exeption.exeptions.*;
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
import static org.mockito.Mockito.*;

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
        id = "c2bfcb8e-af96-43c8-88e2-56f3a78e2436";
        uuid = UUID.fromString(id);
        TestListProduct.clear();
        productList = TestListProduct.PRODUCTS;
        product = productList.get(0);
    }


    // ----------Test methods findById() -------------------------------------

    /**
     * Parameterized method test that checks data validity, converts String id to UUID,
     * after successful conversion, look for this product in List<Product>
     *
     * @param id - unique identifier
     */
    @ParameterizedTest
    @CsvSource(value = {
            "c2bfcb8e-af96-43c8-88e2-56f3a78e2436",
            "097da9b6-aaf2-4b0e-98df-422f74d71446",
            "6752876b-9ef1-4213-bf99-85a810a3d2de",
    })
    void findByIdValidIdTest(String id) {
        product = productList.stream()
                .filter(el -> el.getProductId().equals(UUID.fromString(id)))
                .findFirst().orElse(null);
        uuid = UUID.fromString(id);

        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));
        assertEquals(product, productService.findById(id));
        verify(productRepository).findById(uuid);
    }

    /**
     * Checking the method to ensure that it does not return Null if all the data is correct, and it is guaranteed
     * to return Product
     */
    @Test
    void findByIdValidIdReturnNotNullTest() {
        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));
        assertNotNull(productService.findById(id));
        verify(productRepository).findById(uuid);
    }

    /**
     * Parameterized test that accepts invalid data, or empty data that throws errors
     *
     * @param id        - damaged (length does not match), empty, or does not exist in the id database
     * @param exception - wait for this error
     */
    @ParameterizedTest
    @CsvSource(value = {
            "35026fc0-dbfc-4d52-9c1c-a203929ea63d1231231231231231231,ProductNotFoundException",
            "35026fc0,ProductNotFoundException",
            "35026fc0-dbf-4d5-9c1-a203929ea63d,ProductNotFoundException",
            "351c-a203929ea63d,ProductNotFoundException",
            ",ProductNotFoundException",
            "6752876b-9ef1-4213-bf99-85a810a3d2d2,ProductNotFoundException"
    })
    void findByIdNotValidIdTest(String id, ProductNotFoundException exception) {
        assertThrows(exception.getClass(), () ->
                productService.findById(id));
    }


    // ----------Test methods findByName() -------------------------------------

    /**
     * Parameterized method which, if the given "name" is valid, returns List<Products> and is checked
     * by the size of this List
     *
     * @param expected - the length of the array is expected
     * @param name     - passed valid name
     */
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
        assertEquals(expected, productService.findByName(name).size());
        verify(productRepository).findByName(name);
    }

    /**
     * A test that checks whether this method returns non-null
     */
    @Test
    void findByNameReturnNotNullTest() {
        String name = product.getProductName();
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductName().equals(name))
                        .toList()
        );

        when(productRepository.findByName(name)).thenReturn(products);
        assertNotNull(productService.findByName(name));
        verify(productRepository).findByName(name);
    }

    /**
     * The test checks whether an error is caught if the name "" was passed
     */
    @Test
    void findByNameReturnExceptionNameIsEmptyTest() {
        assertThrows(StringIsNullExceptions.class,
                () -> productService.findByName(""));
    }

    /**
     * The test checks whether an error is caught if the name was passed null
     */
    @Test
    void findByNameReturnExceptionNameIsNullTest() {
        assertThrows(StringIsNullExceptions.class,
                () -> productService.findByName(null));
    }

    // ----------Test methods getAllProducts() -------------------------------------

    /**
     * Method test checks whether the exact quantity of product is returned from the database using List<Product> as an example
     */
    @Test
    void getAllProductsLengthTest() {
        when(productRepository.findAll()).thenReturn(productList);

        assertEquals(38, productService.getAllProducts().size());
        verify(productRepository).findAll();
    }

    /**
     * Test a method that checks if all the data for output is present, so that this method does not return null
     */
    @Test
    void getAllProductsNotNullTest() {
        when(productRepository.findAll()).thenReturn(productList);

        assertNotNull(productService.getAllProducts());
        verify(productRepository).findAll();
    }

    // ----------Test methods getAllProductsByCategories() -------------------------------------

    /**
     * Parameterized test of a method that, given the correct data, should display the correct amount of Product
     *
     * @param expected - number of products in List<Products>
     * @param category - valid product category
     */
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
        assertEquals(expected, productService.getAllProductsByCategory(category).size());
        verify(productRepository).getAllProductsByCategory(productCategory);
    }

    /**
     * Test of a method that, if the data is valid, should return non-null
     */
    @Test
    void getAllProductsByCategoryReturnNotNullTest() {
        String categoryTest = "ELECTRONICS";
        ProductCategory category = ProductCategory.valueOf(categoryTest);
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductCategory().equals(category))
                        .toList()
        );

        when(productRepository.getAllProductsByCategory(category)).thenReturn(products);
        assertNotNull(productService.getAllProductsByCategory(categoryTest));
        verify(productRepository).getAllProductsByCategory(category);
    }

    /**
     * Parameterized test that checks to catch errors if the data that was entered was not valid
     *
     * @param category   - incorrectly written data or its absence in the database
     * @param exceptions - expected error
     */
    @ParameterizedTest
    @CsvSource(value = {
            "SOME CATEGORY,CategoryNotFoundExceptions",
            "sports,CategoryNotFoundExceptions",
            "Sports,CategoryNotFoundExceptions",
            ",CategoryNotFoundExceptions"
    })
    void getAllProductsByCategoryExceptionTest(String category, CategoryNotFoundExceptions exceptions) {
        assertThrows(exceptions.getClass(),
                () -> productService.getAllProductsByCategory(category));
    }

    /**
     * A test that monitors whether an error is thrown if the category was written null
     */
    @Test
    void getAllProductsByCategoryNullExceptionTest() {
        assertThrows(CategoryNotFoundExceptions.class,
                () -> productService.getAllProductsByCategory(null));
    }

    // ----------Test methods getAllProductsByBrand() -------------------------------------

    /**
     * Parameterized test that checks the method if the input data was valid and checks by the quantity of Product
     * at the exit
     *
     * @param expected - number of products
     * @param brand    - the brand by which the product will be searched
     */
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
        assertEquals(expected, productService.getAllProductsByBrand(brand).size());
        verify(productRepository).getAllProductsByBrand(productBrand);
    }

    /**
     * Test of a method that checks if the data is valid and the data in the database is not null
     */
    @Test
    void getAllProductsByBrandNotNullTest() {
        String brandTest = "SAMSUNG";
        ProductBrand productBrand = ProductBrand.valueOf(brandTest);
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductBrand().equals(productBrand))
                        .toList()
        );

        when(productRepository.getAllProductsByBrand(productBrand)).thenReturn(products);
        assertNotNull(productService.getAllProductsByBrand(brandTest));
        verify(productRepository).getAllProductsByBrand(productBrand);
    }

    /**
     * Parameterized test that checks invalid data and expects to receive an error
     *
     * @param brand      - data that is incorrect, or there is no such brand, or an empty parameter
     * @param exceptions - expected error
     */
    @ParameterizedTest
    @CsvSource(value = {
            "SOME BRAND,BrandNotFoundExceptions",
            "SAMSUN,BrandNotFoundExceptions",
            "Samsung,BrandNotFoundExceptions",
            ",BrandNotFoundExceptions"
    })
    void getAllProductsByBrandExceptionTest(String brand, BrandNotFoundExceptions exceptions) {
        assertThrows(exceptions.getClass(),
                () -> productService.getAllProductsByBrand(brand));
    }

    /**
     * A test that monitors whether an error is thrown if the brand was written null
     */
    @Test
    void getAllProductsByBrandNullExceptionTest() {
        assertThrows(BrandNotFoundExceptions.class,
                () -> productService.getAllProductsByBrand(null));
    }

    // ----------Test methods searchProductsByPriceRange() -------------------------------------

    /**
     * Parameterized test that checks if the data is valid and returns as many products as there are in
     * database
     *
     * @param priceMin - minimum price
     * @param priceMax - maximum price
     * @param expected - quantity that is in the database
     */
    @ParameterizedTest
    @CsvSource(value = {
            "20,50,15",
            "50,100,10",
            "100,200,7",
            "200,1000,3"
    })
    void searchProductsByPriceRangeTest(String priceMin, String priceMax, int expected) {
        double priceMinTest = Double.parseDouble(priceMin);
        double priceMaxTest = Double.parseDouble(priceMax);
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> priceMinTest <= el.getProductPrice())
                        .filter(el -> priceMaxTest >= el.getProductPrice())
                        .toList()
        );

        when(productRepository.findByPriceBetween(priceMinTest, priceMaxTest)).thenReturn(products);
        assertEquals(expected, productService.searchProductsByPriceRange(priceMin, priceMax).size());
        verify(productRepository).findByPriceBetween(priceMinTest, priceMaxTest);
    }

    /**
     * Test of a method that checks if the data is valid and the data in the database is not null
     */
    @Test
    void searchProductsByPriceRangeNotNullTest() {
        String priceMin = "20";
        String priceMax = "100";
        double priceMinTest = Double.parseDouble(priceMin);
        double priceMaxTest = Double.parseDouble(priceMax);
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> priceMinTest <= el.getProductPrice())
                        .filter(el -> priceMaxTest >= el.getProductPrice())
                        .toList()
        );

        when(productRepository.findByPriceBetween(priceMinTest, priceMaxTest)).thenReturn(products);
        assertNotNull(productService.searchProductsByPriceRange(priceMin, priceMax));
        verify(productRepository).findByPriceBetween(priceMinTest, priceMaxTest);
    }

    /**
     * Parameterized test that checks for data invalidity if the data cannot be in the sample
     *
     * @param priceMin   - minimum price
     * @param priceMax   - maximum price
     * @param exceptions - expected error
     */
    @ParameterizedTest
    @CsvSource(value = {
            "2%0,50,NumberExceptions",
            "50,1a0,NumberExceptions",
            "1a0,2a0,NumberExceptions",
            "-200,1000,NumberExceptions",
            "200,-1000,NumberExceptions",
            "-10,-500,NumberExceptions",
            "100,5,NumberExceptions",
            "-100,5,NumberExceptions",
            ",1000,NumberExceptions",
            "10,,NumberExceptions",
    })
    void searchProductsByPriceRangeExceptionTest(String priceMin, String priceMax, NumberExceptions exceptions) {
        assertThrows(exceptions.getClass(),
                () -> productService.searchProductsByPriceRange(priceMin, priceMax));
    }

    /**
     * Test to check whether a method handles an error if one of the parameters is null
     */
    @Test
    void searchProductsByPriceMinNullExceptionTest() {
        String priceMax = "10";
        assertThrows(NumberExceptions.class,
                () -> productService.searchProductsByPriceRange(null, priceMax));
    }

    /**
     * Test to check whether a method handles an error if one of the parameters is null
     */
    @Test
    void searchProductsByPriceMaxNullExceptionTest() {
        String priceMin = "10";
        assertThrows(NumberExceptions.class,
                () -> productService.searchProductsByPriceRange(priceMin, null));
    }

    /**
     * Test to check whether a method handles an error if one of the parameters is null
     */
    @Test
    void searchProductsByPriceMaxAndPriceMinNullExceptionTest() {
        assertThrows(NumberExceptions.class,
                () -> productService.searchProductsByPriceRange(null, null));
    }

    // ----------Test methods searchProductsByCategoryBrand() -------------------------------------

    /**
     * Parameterized test that checks, given valid data, whether it displays as many products as
     * meet the conditions for category and brand
     *
     * @param expected - expected number of products
     * @param category - product category
     * @param brand    - product brand
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,ELECTRONICS,APPLE",
            "0,SPORTS,SAMSUNG",
            "4,ELECTRONICS,PHILIPS",
            "2,ELECTRONICS,ASUS",
            "2,SPORTS,LG",
            "1,ELECTRONICS,NIKE",
            "3,OTHER,HASBRO",
            "2,OTHER,BLACK_AND_DECKER",
            "2,SPORTS,ADIDAS",
            "2,ELECTRONICS,DELL"
    })
    void getAllProductsByCategoryBrandTest(int expected, String category, String brand) {
        ProductCategory productCategory = ProductCategory.valueOf(category);
        ProductBrand productBrand = ProductBrand.valueOf(brand);
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductCategory().equals(productCategory))
                        .filter(el -> el.getProductBrand().equals(productBrand))
                        .toList()
        );

        when(productRepository.findByCategoryBrand(productCategory, productBrand)).thenReturn(products);
        assertEquals(expected, productService.searchProductsByCategoryBrand(category, brand).size());
        verify(productRepository).findByCategoryBrand(productCategory, productBrand);
    }

    /**
     * Test of a method that checks if the data is valid and the data in the database is not null
     */
    @Test
    void getAllProductsByCategoryBrandReturnNotNullTest() {
        String categoryTest = "OTHER";
        String brandTest = "HASBRO";
        ProductBrand productBrand = ProductBrand.valueOf(brandTest);
        ProductCategory productCategory = ProductCategory.valueOf(categoryTest);
        List<Product> products = new ArrayList<>(
                productList.stream()
                        .filter(el -> el.getProductCategory().equals(productCategory))
                        .filter(el -> el.getProductBrand().equals(productBrand))
                        .toList()
        );

        when(productRepository.findByCategoryBrand(productCategory, productBrand)).thenReturn(products);
        assertNotNull(productService.searchProductsByCategoryBrand(categoryTest, brandTest));
        verify(productRepository).findByCategoryBrand(productCategory, productBrand);
    }

    /**
     * Parameterized test that checks invalid data and expects to receive an error
     *
     * @param category   - invalid product category, or lack thereof
     * @param brand      - data that is incorrect, or there is no such brand, or an empty parameter
     * @param exceptions - expected error
     */
    @ParameterizedTest
    @CsvSource(value = {
            "ELECTRONICS,SOME BRAND,BrandNotFoundExceptions",
            "OTHEEEEE SOME ,SAMSUNG,CategoryNotFoundExceptions",
            "Electronics,Samsung,CategoryNotFoundExceptions",
            ",SAMSUNG,CategoryNotFoundExceptions",
            "ELECTRONICS,,BrandNotFoundExceptions",
            ",,CategoryNotFoundExceptions"
    })
    void getAllProductsByCategoryBrandEExceptionsTest(String category, String brand, RuntimeException exceptions) {
        assertThrows(exceptions.getClass(),
                () -> productService.searchProductsByCategoryBrand(category, brand));
    }

    /**
     * A test that monitors whether an error is thrown if the brand was written null
     */
    @Test
    void getAllProductsByCategoryBrandBrandEqualsNullTest() {
        String categoryTest = "OTHER";
        assertThrows(BrandNotFoundExceptions.class,
                () -> productService.searchProductsByCategoryBrand(categoryTest, null));
    }

    /**
     * A test that monitors whether an error is thrown if the category was written null
     */
    @Test
    void getAllProductsByCategoryBrandCategoryEqualsNullTest() {
        String brandTest = "HASBRO";
        assertThrows(CategoryNotFoundExceptions.class,
                () -> productService.searchProductsByCategoryBrand(null, brandTest));
    }

    /**
     * A test that monitors whether an error is thrown if the category and brand was written null
     */
    @Test
    void getAllProductsByCategoryBrandCategoryAndBrandEqualsNullTest() {
        assertThrows(CategoryNotFoundExceptions.class,
                () -> productService.searchProductsByCategoryBrand(null, null));
    }

    // ----------Test methods createProduct() -------------------------------------

    /**
     * The test checks whether it returns the same object if the data was valid
     */
//    @Test
//    void createProductTest() {
//        when(productRepository.save(product)).thenReturn(productList.get(0));
//        Product product1 = productList.get(0);
//        assertEquals(product1, productService.createProduct(product));
//        verify(productRepository).save(product);
//    }

    /**
     * The test checks whether the method returns non-null if the data was valid
     */
//    @Test
//    void createProductReturnNotNullTest() {
//        when(productRepository.save(product)).thenReturn(productList.get(0));
//        assertNotNull(productService.createProduct(product));
//        verify(productRepository).save(product);
//    }

    /**
     * Method test that checks if an invalid null value was passed to the method
     */
    @Test
    void createProductExceptionsTest() {
        assertThrows(NullPointerException.class, () -> productService.createProduct(null));
    }

    // ----------Test methods updateProductParamById() -------------------------------------

    /**
     * Parameterized test that checks whether the data in the class changes if all the data was correct
     *
     * @param tableName - table name
     */
    @ParameterizedTest
    @CsvSource(value = {
            "name",
            "NAME",
            "NaMe"
    })
    void updateProductParamByIdTableNameTest(String tableName) {
        String name = "New Name";

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        doNothing().when(productRepository).updateProductName(uuid, name);

        product.setProductName(name);
        productService.updateProductParamById(id, tableName, name);
        assertEquals(name, product.getProductName());
        verify(productRepository).findById(uuid);
        verify(productRepository).updateProductName(uuid, name);
    }

    /**
     * Parameterized test that checks whether the data in the class changes if all the data was correct
     *
     * @param tableName - table name
     */
    @ParameterizedTest
    @CsvSource(value = {
            "price",
            "PrIcE",
            "PRICE",
    })
    void updateProductParamByIdTablePriceTest(String tableName) {
        String price = "100";
        double priceTest = Double.parseDouble(price);

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        doNothing().when(productRepository).updateProductPrice(uuid, price);

        product.setProductPrice(priceTest);
        productService.updateProductParamById(id, tableName, price);
        assertEquals(priceTest, product.getProductPrice());
        verify(productRepository).findById(uuid);
        verify(productRepository).updateProductPrice(uuid, price);
    }

    /**
     * Parameterized test that checks whether the data in the class changes if all the data was correct
     *
     * @param tableName - table name
     */
    @ParameterizedTest
    @CsvSource(value = {
            "descriptions",
            "DeScRiPtIonS",
            "DESCRIPTIONS",
    })
    void updateProductParamByIdTableDescriptionsTest(String tableName) {
        String descriptions = "Some New Description";

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        doNothing().when(productRepository).updateProductDescriptions(uuid, descriptions);

        product.setProductDescription(descriptions);
        productService.updateProductParamById(id, tableName, descriptions);
        assertEquals(descriptions, product.getProductDescription());
        verify(productRepository).findById(uuid);
        verify(productRepository).updateProductDescriptions(uuid, descriptions);
    }

    /**
     * Parameterized test that checks whether the data in the class changes if all the data was correct
     *
     * @param tableName - table name
     */
    @ParameterizedTest
    @CsvSource(value = {
            "category",
            "CaTeGoRy",
            "CATEGORY",
    })
    void updateProductParamByIdTableCategoryTest(String tableName) {
        String category = "ELECTRONICS";
        ProductCategory categoryTest = ProductCategory.valueOf(category);

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        doNothing().when(productRepository).updateProductCategory(uuid, categoryTest);

        product.setProductCategory(categoryTest);
        productService.updateProductParamById(id, tableName, category);
        assertEquals(categoryTest, product.getProductCategory());
        verify(productRepository).findById(uuid);
        verify(productRepository).updateProductCategory(uuid, categoryTest);
    }

    /**
     * Parameterized test that checks whether the data in the class changes if all the data was correct
     *
     * @param tableName - table name
     */
    @ParameterizedTest
    @CsvSource(value = {
            "BRAND",
            "brand",
            "BrAnD",
    })
    void updateProductParamByIdTableBrandTest(String tableName) {
        String brand = "APPLE";
        ProductBrand brandTest = ProductBrand.valueOf(brand);

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        doNothing().when(productRepository).updateProductBrand(uuid, brandTest);

        product.setProductBrand(brandTest);
        productService.updateProductParamById(id, tableName, brand);
        assertEquals(brandTest, product.getProductBrand());
        verify(productRepository).findById(uuid);
        verify(productRepository).updateProductBrand(uuid, brandTest);
    }

    /**
     * Test of a method that throws an exception if the id data is invalid
     *
     * @param id - invalid id
     */
    @ParameterizedTest
    @CsvSource(value = {
            "12345678765432",
            "1213",
            "c2bfcb8e-af96-43c8-88e2-56f3a78e2435",
            "c2bfcb8e-af96-43c8-88e2-56f3a78e24362342"
    })
    void updateProductParamByIdIsWrongTest(String id) {
        String tableName = "name";
        String value = "Some Name";
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProductParamById(id, tableName, value));
    }

    /**
     * Test of a method that throws an exception if the id data is invalid
     */
    @Test
    void updateProductParamByIdIsNullTest() {
        String tableName = "name";
        String value = "Some Name";
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProductParamById(null, tableName, value));
    }

    /**
     * Test of a method that throws an exception if the id data is invalid
     */
    @Test
    void updateProductParamByIdValueIsEmptyTest() {
        String id = "c2bfcb8e-af96-43c8-88e2-56f3a78e2435";
        uuid = UUID.fromString(id);
        String tableName = "name";
        String value = "";

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        assertThrows(StringIsNullExceptions.class,
                () -> productService.updateProductParamById(id, tableName, value));
        verify(productRepository).findById(uuid);
    }

    /**
     * Test of a method that throws an exception if the value data is invalid
     */
    @Test
    void updateProductParamByIdValueIsNullTest() {
        String id = "c2bfcb8e-af96-43c8-88e2-56f3a78e2435";
        uuid = UUID.fromString(id);
        String tableName = "name";

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        assertThrows(StringIsNullExceptions.class,
                () -> productService.updateProductParamById(id, tableName, null));
        verify(productRepository).findById(uuid);
    }

    /**
     * Test of a method that throws an exception if the value data is invalid
     *
     * @param value - invalid number value
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1aad1",
            "-100",
            "241asd12"
    })
    void updateProductParamByIdValuePriceTest(String value) {
        String id = "c2bfcb8e-af96-43c8-88e2-56f3a78e2435";
        uuid = UUID.fromString(id);
        String tableName = "price";

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        assertThrows(NumberExceptions.class,
                () -> productService.updateProductParamById(id, tableName, value));
        verify(productRepository).findById(uuid);
    }

    /**
     * Test of a method that throws an exception if the category data is invalid
     *
     * @param category - invalid category value
     */
    @ParameterizedTest
    @CsvSource(value = {
            "SOME CATEGORY",
            "sports",
            "Sports",
    })
    void updateProductParamByIdValueCategoryTest(String category) {
        String id = "c2bfcb8e-af96-43c8-88e2-56f3a78e2435";
        uuid = UUID.fromString(id);
        String tableName = "category";

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        assertThrows(CategoryNotFoundExceptions.class,
                () -> productService.updateProductParamById(id, tableName, category));
        verify(productRepository).findById(uuid);
    }

    /**
     * Test of a method that throws an exception if the brand data is invalid
     *
     * @param brand - invalid brand value
     */
    @ParameterizedTest
    @CsvSource(value = {
            "SOME Brand",
            "Samsung",
            "APPLES",
    })
    void updateProductParamByIdValueBrandTest(String brand) {
        String id = "c2bfcb8e-af96-43c8-88e2-56f3a78e2435";
        uuid = UUID.fromString(id);
        String tableName = "brand";

        when(productRepository.findById(uuid)).thenReturn(Optional.ofNullable(product));
        assertThrows(BrandNotFoundExceptions.class,
                () -> productService.updateProductParamById(id, tableName, brand));
        verify(productRepository).findById(uuid);
    }

    // ----------Test methods deleteById() -------------------------------------

    /**
     * Test of a method that throws an exception if the id data is invalid
     *
     * @param id - invalid id
     */
    @ParameterizedTest
    @CsvSource(value = {
            "12345678765432",
            "1213",
            "c2bfcb8e-af96-43c8-88e2-56f3a78e2435",
            "c2bfcb8e-af96-43c8-88e2-56f3a78e24362342"
    })
    void deleteByIdIdIsWrongTest(String id) {
        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteById(id));
    }
}
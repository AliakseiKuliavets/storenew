package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.aliakseikul.storenew.exeption.exeptions.*;
import com.aliakseikul.storenew.exeption.message.ErrorMessage;
import com.aliakseikul.storenew.repository.ProductRepository;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(String id) {
        checkIdLength(id);
        return productRepository.findById(UUID.fromString(id)).
                orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> findByName(String name) {
        if (valueNullOrEmpty(name)) {
            throw new ProductNotFoundException(ErrorMessage.NULL_OR_EMPTY);
        }
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        checkCategory(category);
        return productRepository.getAllProductsByCategory(ProductCategory.valueOf(category));
    }

    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        checkBrand(brand);
        return productRepository.getAllProductsByBrand(ProductBrand.valueOf(brand));
    }

    @Override
    public List<Product> searchProductsByPriceRange(String minPrice, String maxPrice) {
        checkNumber(minPrice);
        checkNumber(maxPrice);
        return productRepository.findByPriceBetween(Double.parseDouble(minPrice), Double.parseDouble(maxPrice));
    }


    @Override
    public List<Product> searchProductsByCategoryBrand(String category, String brand) {
        checkCategory(category);
        checkBrand(brand);
        return productRepository.findByCategoryBrand(ProductCategory.valueOf(category), ProductBrand.valueOf(brand));
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateProductParamById(String productId, String tableName, String value) {
        if (valueNullOrEmpty(tableName)) {
            throw new TablesNotFoundExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        checkId(productId);
        UUID productUuid = UUID.fromString(productId);

        String responseMessage;
        switch (tableName.toLowerCase()) {
            case "name":
                if (valueNullOrEmpty(value)) {
                    throw new ProductNotFoundException(ErrorMessage.NULL_OR_EMPTY);
                }
                productRepository.updateProductName(productUuid, value);
                responseMessage = "set new name " + value;
                break;
            case "price":
                checkNumber(value);
                productRepository.updateProductPrice(productUuid, value);
                responseMessage = "set new price " + value;
                break;
            case "descriptions":
                productRepository.updateProductDescriptions(productUuid, value);
                responseMessage = "set new descriptions " + value;
                break;
            case "category":
                checkCategory(value);
                productRepository.updateProductCategory(productUuid, ProductCategory.valueOf(value));
                responseMessage = "set new category " + value;
                break;
            case "brand":
                checkBrand(value);
                productRepository.updateProductBrand(productUuid, ProductBrand.valueOf(value));
                responseMessage = "set new phone brand " + value;
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid property: " + tableName);
        }
        return ResponseEntity.ok("Product with ID " + productId + " " + responseMessage);
    }

    @Override
    public void deleteById(String productId) {
        checkId(productId);
        productRepository.deleteById(UUID.fromString(productId));
    }

    private boolean valueNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private void checkIdLength(String productId) {
        if (valueNullOrEmpty(productId)) {
            throw new ProductNotFoundException(ErrorMessage.NULL_OR_EMPTY);
        }
        if (productId.length() != 36) {
            throw new ProductNotFoundException(ErrorMessage.WRONG_ID_LENGTH);
        }
    }

    private void checkId(String productId) {
        if (findById(productId) == null) {
            throw new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        }
    }

    private void checkCategory(String category) {
        if (valueNullOrEmpty(category)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        List<String> productCategoryList = Arrays.asList(
                String.valueOf(ProductCategory.ELECTRONICS),
                String.valueOf(ProductCategory.SPORTS),
                String.valueOf(ProductCategory.OTHER)
        );
        if (!(productCategoryList.contains(category))) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
    }

    private void checkBrand(String brand) {
        if (valueNullOrEmpty(brand)) {
            throw new BrandNotFoundExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        List<String> productBrandList = Arrays.asList(
                String.valueOf(ProductBrand.DELL),
                String.valueOf(ProductBrand.LG),
                String.valueOf(ProductBrand.ADIDAS),
                String.valueOf(ProductBrand.APPLE),
                String.valueOf(ProductBrand.ASUS),
                String.valueOf(ProductBrand.BLACK_AND_DECKER),
                String.valueOf(ProductBrand.HASBRO),
                String.valueOf(ProductBrand.NIKE),
                String.valueOf(ProductBrand.PHILIPS),
                String.valueOf(ProductBrand.SAMSUNG)
        );
        if (!(productBrandList.contains(brand))) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }
    }

    private void checkNumber(String number) {
        if (valueNullOrEmpty(number)) {
            throw new NumberExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        char[] chars = number.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
            }
        }
    }
}

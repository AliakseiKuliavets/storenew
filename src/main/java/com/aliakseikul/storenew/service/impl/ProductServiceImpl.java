package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.aliakseikul.storenew.exeption.exeptions.ProductNotFoundException;
import com.aliakseikul.storenew.exeption.message.ErrorMessage;
import com.aliakseikul.storenew.repository.ProductRepository;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(String id) {
        checkIdLength(id);
        return productRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.getAllProductsByCategory(ProductCategory.valueOf(category));
    }

    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        return productRepository.getAllProductsByBrand(ProductBrand.valueOf(brand));
    }

    @Override
    public List<Product> searchProductsByPriceRange(String minPrice, String maxPrice) {
        return productRepository.findByPriceBetween(Double.parseDouble(minPrice), Double.parseDouble(maxPrice));
    }

    @Override
    public List<Product> searchProductsByCategoryBrand(String category, String brand) {
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
        checkId(productId);
        UUID productUuid = UUID.fromString(productId);

        String responseMessage;
        switch (tableName.toLowerCase()) {
            case "name":
                productRepository.updateProductName(productUuid, value);
                responseMessage = "set new name " + value;
                break;
            case "price":
                productRepository.updateProductPrice(productUuid, value);
                responseMessage = "set new price " + value;
                break;
            case "descriptions":
                productRepository.updateProductDescriptions(productUuid, value);
                responseMessage = "set new descriptions " + value;
                break;
            case "category":
                productRepository.updateProductCategory(productUuid, ProductCategory.valueOf(value));
                responseMessage = "set new category " + value;
                break;
            case "brand":
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

    private void checkId(String productId) {
        if (findById(productId) == null) {
            throw new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        }
    }

    private void checkIdLength(String productId) {
        if (productId.isEmpty()) {
            throw new ProductNotFoundException(ErrorMessage.WRONG_ID);
        }
        if (productId.length() != 36) {
            throw new ProductNotFoundException(ErrorMessage.WRONG_ID_LENGTH);
        }
    }
}

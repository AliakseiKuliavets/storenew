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

import static com.aliakseikul.storenew.exeption.checkMethods.Check.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(String id) {
        if (checkIdLength(id)) {
            throw new ProductNotFoundException(ErrorMessage.WRONG_ID_LENGTH);
        }
        return productRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> findByName(String name) {
        if (valueNullOrEmpty(name)) {
            throw new StringIsNullExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        if (checkCategory(category)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
        return productRepository.getAllProductsByCategory(ProductCategory.valueOf(category));
    }

    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        if (checkBrand(brand)) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }
        return productRepository.getAllProductsByBrand(ProductBrand.valueOf(brand));
    }

    @Override
    public List<Product> searchProductsByPriceRange(String minPrice, String maxPrice) {
        if (valueNullOrEmpty(minPrice) || valueNullOrEmpty(maxPrice)) {
            throw new NumberExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        if (checkNumber(minPrice) || checkNumber(maxPrice)) {
            throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
        }
        double minPriceS = Double.parseDouble(minPrice);
        double maxPriceS = Double.parseDouble(maxPrice);
        if (minPriceS < 0 || maxPriceS < 0) {
            throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
        }
        if (minPriceS > maxPriceS) {
            throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
        }
        return productRepository.findByPriceBetween(minPriceS, maxPriceS);
    }

    @Override
    public List<Product> searchProductsByCategoryBrand(String category, String brand) {
        if (checkCategory(category)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
        if (checkBrand(brand)) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }
        return productRepository.findByCategoryBrand(ProductCategory.valueOf(category), ProductBrand.valueOf(brand));
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        if (product == null) {
            throw new NullPointerException(ErrorMessage.NULL_OR_EMPTY);
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateProductParamById(String productId, String tableName, String value) {
        if (checkId(productId)) {
            throw new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        }
        if (valueNullOrEmpty(tableName) || valueNullOrEmpty(value)) {
            throw new StringIsNullExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        UUID productUuid = UUID.fromString(productId);

        String responseMessage;
        switch (tableName.toLowerCase()) {
            case "name":
                productRepository.updateProductName(productUuid, value);
                responseMessage = "set new name " + value;
                break;
            case "price":
                if (checkNumber(value)) {
                    throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
                }
                double number = Double.parseDouble(value);
                if (number < 0) {
                    throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
                }
                productRepository.updateProductPrice(productUuid, value);
                responseMessage = "set new price " + value;
                break;
            case "descriptions":
                productRepository.updateProductDescriptions(productUuid, value);
                responseMessage = "set new descriptions " + value;
                break;
            case "category":
                if (checkCategory(value)) {
                    throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
                } else {
                    productRepository.updateProductCategory(productUuid, ProductCategory.valueOf(value));
                    responseMessage = "set new category " + value;
                    break;
                }
            case "brand":
                if (checkBrand(value)) {
                    throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
                } else {
                    productRepository.updateProductBrand(productUuid, ProductBrand.valueOf(value));
                    responseMessage = "set new phone brand " + value;
                    break;
                }
            default:
                return ResponseEntity.badRequest().body("Invalid property: " + tableName);
        }
        return ResponseEntity.ok("Product with ID " + productId + " " + responseMessage);
    }

    @Override
    public void deleteById(String productId) {
        if (checkId(productId)) {
            throw new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(UUID.fromString(productId));
    }

    private boolean checkId(String productId) {
        if (valueNullOrEmpty(productId)) {
            throw new ProductNotFoundException(ErrorMessage.NULL_OR_EMPTY);
        }
        return findById(productId) == null;
    }

    private boolean checkCategory(String category) {
        if (valueNullOrEmpty(category)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        List<String> productCategoryList = Arrays.asList(
                String.valueOf(ProductCategory.ELECTRONICS),
                String.valueOf(ProductCategory.SPORTS),
                String.valueOf(ProductCategory.OTHER)
        );
        return !(productCategoryList.contains(category));
    }

    private boolean checkBrand(String brand) {
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
        return !(productBrandList.contains(brand));
    }
}

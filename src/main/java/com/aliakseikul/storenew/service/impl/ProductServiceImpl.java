package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.aliakseikul.storenew.exception.exeptions.*;
import com.aliakseikul.storenew.exception.message.ErrorMessage;
import com.aliakseikul.storenew.mapper.ProductMapper;
import com.aliakseikul.storenew.repository.ProductRepository;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.aliakseikul.storenew.exception.checkMethods.Check.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ProductMapper productMapper;

    @Override
    public Product findById(String id) {
        if (checkIdLength(id)) {
            throw new ProductNotFoundException(ErrorMessage.WRONG_ID_LENGTH);
        }
        return productRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<ProductDto> findByName(String name) {
        if (checkString45Length(name)) {
            throw new StringNotCorrectException(ErrorMessage.STRING_WRONG_LENGTH);
        }
        return productMapper.productsToProductsDto(productRepository.findByName(name));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.productsToProductsDto(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getAllProductsByCategory(String category) {
        String categoryUp = category.toUpperCase();
        if (checkCategory(categoryUp)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getAllProductsByCategory(ProductCategory.valueOf(categoryUp)));
    }

    @Override
    public List<ProductDto> getAllProductsByBrand(String brand) {
        String brandUp = brand.toUpperCase();
        if (checkBrand(brandUp)) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getAllProductsByBrand(ProductBrand.valueOf(brandUp)));
    }

    @Override
    public List<ProductDto> searchProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        double minPriceS = minPrice.doubleValue();
        double maxPriceS = maxPrice.doubleValue();
        if (minPriceS < 0 || maxPriceS < 0) {
            throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
        }
        if (minPriceS > maxPriceS) {
            throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
        }
        return productMapper.productsToProductsDto(productRepository.findByPriceBetween(minPriceS, maxPriceS));
    }

    @Override
    public List<ProductDto> searchProductsByCategoryBrand(String category, String brand) {
        if (checkCategory(category)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
        if (checkBrand(brand)) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.findByCategoryBrand(ProductCategory.valueOf(category), ProductBrand.valueOf(brand)));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        User placedByUser = userRepository.findUserByNickName(productDto.getUserNickname());
        if (placedByUser == null) {
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }
        Product product = Product.builder()
                .productName(productDto.getProductName())
                .productPrice(productDto.getProductPrice())
                .productDescription(productDto.getProductDescription())
                .productCategory(productDto.getProductCategory())
                .productBrand(productDto.getProductBrand())
                .placedByUser(placedByUser)
                .build();
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateProductParamById(String productId, String tableName, String value) {
        findById(productId);
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
        findById(productId);
        productRepository.deleteById(UUID.fromString(productId));
    }

    private boolean checkCategory(String category) {
        return !(ProductCategory.getProductCategoryList().contains(category));
    }

    private boolean checkBrand(String brand) {
        return !(ProductBrand.getProductBrandList().contains(brand));
    }
}

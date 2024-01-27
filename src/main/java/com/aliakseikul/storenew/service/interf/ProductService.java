package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product findById(String id);

    List<ProductDto> findByName(String name);

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByCategory(String category);

    List<ProductDto> getAllProductsByBrand(String brand);

    List<ProductDto> searchProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<ProductDto> searchProductsByCategoryBrand(String category, String brand);

    ProductDto createProduct(ProductDto productDto);

    ResponseEntity<String> updateProductParamById(String productId, String tableName, String value);

    void deleteById(String productId);

}

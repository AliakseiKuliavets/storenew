package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ProductDto findById(String id);

    List<ProductDto> findByName(String name);

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByCategory(String category);

    List<ProductDto> getAllProductsByBrand(String brand);

    List<ProductDto> searchProductsByPriceRange(String minPrice, String maxPrice);

    List<ProductDto> searchProductsByCategoryBrand(String category, String brand);

    ProductDto createProduct(ProductDto productDto);

    ResponseEntity<String> updateProductParamById(String productId, String tableName, String value);

    void deleteById(String productId);

}

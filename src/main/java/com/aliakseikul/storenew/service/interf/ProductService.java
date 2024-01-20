package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    Product findById(String id);

    List<Product> findByName(String name);

    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(String category);

    List<Product> getAllProductsByBrand(String brand);

    List<Product> searchProductsByPriceRange(String minPrice, String maxPrice);

    List<Product> searchProductsByCategoryBrand(String category, String brand);

    Product createProduct(Product product);

    ResponseEntity<String> updateProductParamById(String productId, String tableName, String value);

    void deleteById(String productId);

}

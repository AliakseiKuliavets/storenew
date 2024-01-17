package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.entity.Product;

import java.util.List;

public interface ProductService {

    Product findById(String id);

    Product findByName(String name);

    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(String category);

    List<Product> getAllProductsByBrand(String brand);

    List<Product> searchProductsByPriceRange(String minPrice, String maxPrice);

    List<Product> searchProductsByCategoryBrand(String category, String brand);

    Product createProduct(Product product);

    void updateProductName(String id, String name);

    void updateProductPrice(String productId, String value);

    void updateProductDescriptions(String productId, String value);

    void updateProductCategory(String productId, String value);

    void updateProductBrand(String productId, String value);

    void deleteById(String productId);
}

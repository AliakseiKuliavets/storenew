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
}

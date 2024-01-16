package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.aliakseikul.storenew.repository.ProductRepository;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
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
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductName(String id, String name) {
        productRepository.updateProductName(UUID.fromString(id),name);
    }

    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(UUID.fromString(productId));
    }
}

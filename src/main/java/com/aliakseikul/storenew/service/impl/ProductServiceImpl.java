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
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductName(String productId, String name) {
        productRepository.updateProductName(UUID.fromString(productId),name);
    }

    @Override
    @Transactional
    public void updateProductPrice(String productId, String price) {
        productRepository.updateProductPrice(UUID.fromString(productId),price);
    }

    @Override
    @Transactional
    public void updateProductDescriptions(String productId, String descriptions) {
        productRepository.updateProductDescriptions(UUID.fromString(productId),descriptions);
    }

    @Override
    @Transactional
    public void updateProductCategory(String productId, String category) {
        productRepository.updateProductCategory(UUID.fromString(productId),ProductCategory.valueOf(category));
    }

    @Override
    @Transactional
    public void updateProductBrand(String productId, String brand) {
        productRepository.updateProductBrand(UUID.fromString(productId),ProductBrand.valueOf(brand));
    }

    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(UUID.fromString(productId));
    }
}

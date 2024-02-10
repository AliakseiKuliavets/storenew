package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface ProductService {

    Product findById(String id);

    List<ProductDto> findByName(String name);

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByCategory(String category);

    List<ProductDto> getAllProductsByBrand(String brand);

    List<ProductDto> searchProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<ProductDto> searchProductsByCategoryBrand(String category, String brand);

    List<ProductDto> searchProductsByCategoryBrandAndName(String category, String brand, String name);

    ProductDto createProduct(ProductDto productDto);

    void createProduct(Principal principal, ProductDto productDto, MultipartFile file1);

    void updateProductNameWithId(String productId, String name);

    void deleteById(String productId);

}

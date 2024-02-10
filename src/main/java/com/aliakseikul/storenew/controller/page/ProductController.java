package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/id")
    public Product getProductById(
            @NotNull @IdChecker @RequestParam String id
    ) {
        return productService.findById(id);
    }

    @GetMapping("/name")
    public List<ProductDto> getProductByName(
            @NotNull @Size(min = 1, max = 44) @RequestParam String name
    ) {
        return productService.findByName(name);
    }

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/allByCategory/")
    public List<ProductDto> getAllProductsByCategory(
            @NotNull @Size(min = 1, max = 44) @RequestParam String category
    ) {
        return productService.getAllProductsByCategory(category);
    }

    @GetMapping("/allByBrand/")
    public List<ProductDto> getAllProductsByBrand(
            @NotNull @Size(min = 1, max = 44) @RequestParam String brand
    ) {
        return productService.getAllProductsByBrand(brand);
    }

    @GetMapping("/allByPrice/search")
    public List<ProductDto> searchProductsByPriceRange(
            @NotNull
            @Positive
            @DecimalMin(value = "0.01", message = "Price should be greater than 0")
            @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00")
            @RequestParam BigDecimal minPrice,
            @NotNull
            @Positive
            @DecimalMin(value = "0.01", message = "Price should be greater than 0")
            @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00")
            @RequestParam BigDecimal maxPrice
    ) {
        return productService.searchProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/allByCategoryBrand/search")
    public List<ProductDto> searchProductsByCategoryBrand(
            @NotNull @Size(min = 1, max = 44) @RequestParam String category,
            @NotNull @Size(min = 1, max = 44) @RequestParam String brand
    ) {
        return productService.searchProductsByCategoryBrand(category, brand);
    }

    @PostMapping("/create")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PostMapping("/update/{productId}and{productName}")
    public void updateProductNameWithId(
            @NotNull @IdChecker @PathVariable("productId") String productId,
            @NotNull @PathVariable("productName") String name
    ) {
        productService.updateProductNameWithId(productId, name);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> deleteById(
            @NotNull @IdChecker @PathVariable("productId") String productId
    ) {
        productService.deleteById(productId);
        return ResponseEntity.ok("Product with ID " + productId + " has been deleted");
    }
}

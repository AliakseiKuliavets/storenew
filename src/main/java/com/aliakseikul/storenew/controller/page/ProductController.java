package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import com.aliakseikul.storenew.validation.interf.Str45LengthCheck;
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

    @GetMapping("/")
    public Product getProductById(@IdChecker @RequestParam String id) {
        return productService.findById(id);
    }

    @GetMapping("/name")
    public List<ProductDto> getProductByName(@Str45LengthCheck @RequestParam String name) {
        return productService.findByName(name);
    }

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/allByCategory/")
    public List<ProductDto> getAllProductsByCategory(@RequestParam String category) {
        return productService.getAllProductsByCategory(category);
    }

    @GetMapping("/allByBrand/")
    public List<ProductDto> getAllProductsByBrand(@RequestParam String brand) {
        return productService.getAllProductsByBrand(brand);
    }

    @GetMapping("/allByPrice/search")
    public List<ProductDto> searchProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return productService.searchProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/allByCategoryBrand/search")
    public List<ProductDto> searchProductsByCategoryBrand(
            @RequestParam String category,
            @RequestParam String brand
    ) {
        return productService.searchProductsByCategoryBrand(category, brand);
    }

    @PostMapping("/create")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/update/")
    public ResponseEntity<String> updateProductPropertyId(
            @IdChecker @RequestParam String productId,
            @Str45LengthCheck @RequestParam String tableName,
            @Str45LengthCheck @RequestParam String value
    ) {
        return productService.updateProductParamById(productId, tableName, value);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> deleteById(@IdChecker @PathVariable("productId") String productId) {
        productService.deleteById(productId);
        return ResponseEntity.ok("Product with ID " + productId + " has been deleted");
    }
}

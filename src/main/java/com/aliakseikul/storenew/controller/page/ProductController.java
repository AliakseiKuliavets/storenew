package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public Product getProductById(@RequestParam String id) {
        return productService.findById(id);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/allByCategory/")
    public List<Product> getAllProductsByCategory(@RequestParam String category) {
        return productService.getAllProductsByCategory(category);
    }

    @GetMapping("/allByBrand/")
    public List<Product> getAllProductsByBrand(@RequestParam String brand) {
        return productService.getAllProductsByBrand(brand);
    }

    @GetMapping("/allByPrice/search")
    public List<Product> searchProductsByPriceRange(
            @RequestParam String minPrice,
            @RequestParam String maxPrice) {
        return productService.searchProductsByPriceRange(minPrice,maxPrice);
    }
}

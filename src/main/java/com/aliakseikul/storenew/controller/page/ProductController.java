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

    @GetMapping("/") //http://localhost:8080/api/product/?id=35026fc0-dbfc-4d52-9c1c-a203929ea63d
    public Product getProductById(@RequestParam String id) {
        return productService.findById(id);
    }

    @GetMapping("/name") //http://localhost:8080/api/product/name?name=Iphone%208
    public Product getProductByName(@RequestParam String name) {
        return productService.findByName(name);
    }

    @GetMapping("/all") //http://localhost:8080/api/product/all
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/allByCategory/") //http://localhost:8080/api/product/allByCategory/?category=ELECTRONICS
    public List<Product> getAllProductsByCategory(@RequestParam String category) {
        return productService.getAllProductsByCategory(category);
    }

    @GetMapping("/allByBrand/") //http://localhost:8080/api/product/allByBrand/?brand=APPLE
    public List<Product> getAllProductsByBrand(@RequestParam String brand) {
        return productService.getAllProductsByBrand(brand);
    }

    //http://localhost:8080/api/product/allByPrice/search?minPrice=1000.00&maxPrice=2000
    @GetMapping("/allByPrice/search")
    public List<Product> searchProductsByPriceRange(
            @RequestParam String minPrice,
            @RequestParam String maxPrice) {
        return productService.searchProductsByPriceRange(minPrice,maxPrice);
    }
}

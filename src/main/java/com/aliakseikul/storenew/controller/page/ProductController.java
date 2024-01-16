package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return productService.searchProductsByPriceRange(minPrice, maxPrice);
    }

    //http://localhost:8080/api/product/allByCategoryBrand/search?category=ELECTRONICS&brand=APPLE
    @GetMapping("/allByCategoryBrand/search")
    public List<Product> searchProductsByCategoryBrand(
            @RequestParam String category,
            @RequestParam String brand) {
        return productService.searchProductsByCategoryBrand(category, brand);
    }

    @PostMapping("/create") //http://localhost:8080/api/product/create
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    /*
     {
    "productName": "Iphone 10",
    "productPrice": 2500.0,
    "productDescription": "Simple description",
    "productCategory": "ELECTRONICS",
    "productBrand": "APPLE",
    "placedByUser": {
        "userId": "a197d1bb-8990-4b08-ad8a-9ec55718fcb8"
        }
    }
     */

    @PutMapping("/update/")
    public ResponseEntity<String> updateProductName(
            @RequestParam String id,
            @RequestParam String name) {
        productService.updateProductName(id, name);
        return ResponseEntity.ok("Product with ID " + id + " has been update name " + name);
    }
    //http://localhost:8080/api/product/update/?id=35026fc0-dbfc-4d52-9c1c-a203929ea63d&name=Some

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> deleteById(@PathVariable("productId") String productId) {
        productService.deleteById(productId);
        return ResponseEntity.ok("Product with ID " + productId + " has been deleted");
    }
    //http://localhost:8080/api/product/remove/35026fc0-dbfc-4d52-9c1c-a203929ea63d
}

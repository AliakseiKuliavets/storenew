package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.ProductRequest;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.service.interf.ProductService;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

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

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }
    /*
    {
     "productId": "48f84933-0baf-435b-9411-6913fc1c3952",
    "productName": "Iphone 10",
    "productPrice": 2500.0,
    "productDescription": "Simple description",
    "productCategory": "ELECTRONICS",
    "productBrand": "APPLE",
    "placedByUser": {
        "userId": "a197d1bb-8990-4b08-ad8a-9ec55718fcb8",
        "userFirstName": "Alexander",
        "userLastName": "Karadiaur",
        "userEmail": "alex@gmail.com",
        "userPhoneNumber": "+497576152478",
        "userVerifiedAccount": true
        }
    }
     */
    @PostMapping("/createTest")
    public ResponseEntity<Product> createProductTest(@RequestBody ProductRequest productRequest) {
        String placedByUserId = productRequest.getPlacedByUser();
        User placedByUser = userService.findById(placedByUserId);

        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductPrice(productRequest.getProductPrice());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductCategory(productRequest.getProductCategory());
        product.setProductBrand(productRequest.getProductBrand());
        product.setPlacedByUser(placedByUser);

        Product createdProduct = productService.create(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

}

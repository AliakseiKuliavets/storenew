package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.findById(id);
    }
}

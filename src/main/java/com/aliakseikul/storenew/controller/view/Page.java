package com.aliakseikul.storenew.controller.view;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/start")
public class Page {

    private final ProductService productService;
    private final ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/")
    public ModelAndView welcome(Model model) {
        modelAndView.setViewName("products.html");
        model.addAttribute("products", productService.getAllProducts());
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getProductById(@PathVariable String id) {
        Product product = productService.findById(id);
        modelAndView.setViewName("products.info.html");
        modelAndView.addObject("product", product);
        modelAndView.addObject("images", product.getImages());
        return modelAndView;
    }

    @PostMapping("/create")
    public void createProduct(
            @RequestParam("file1") MultipartFile file1,
            @ModelAttribute ProductDto productDto
    ) {
        productService.createProduct(productDto, file1);
    }

    @PostMapping("/remove/{productId}")
    public ModelAndView deleteById(@PathVariable("productId") String productId, Model model) {
        productService.deleteById(productId);
        model.addAttribute("products", productService.getAllProducts());
        modelAndView.setViewName("products.html");
        return welcome(model);
    }
}

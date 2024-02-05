package com.aliakseikul.storenew.controller.view;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/start")
public class Page {

    private final ProductService productService;
    private final ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/")
    public ModelAndView welcome(
            @RequestParam(name = "name", required = false) String name,
            Principal principal,
            Model model
    ) {
        modelAndView.setViewName("products.html");
        model.addAttribute("products", productService.findByName(name));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
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
            Principal principal,
            @RequestParam("file1") MultipartFile file1,
            @ModelAttribute ProductDto productDto
    ) {
        productService.createProduct(principal, productDto, file1);
    }

    @PostMapping("/remove/{productId}")
    public ModelAndView deleteById(
            @RequestParam(name = "name", required = false) String name,
            @PathVariable("productId") String productId,
            Model model,
            Principal principal
    ) {
        productService.deleteById(productId);
        model.addAttribute("products", productService.getAllProducts());
        modelAndView.setViewName("products.html");
        return welcome(name,principal, model);
    }
}

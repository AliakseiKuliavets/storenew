package com.aliakseikul.storenew.controller.view;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.dto.auth.AuthenticationRequest;
import com.aliakseikul.storenew.dto.auth.RegisterRequest;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import com.aliakseikul.storenew.service.interf.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class Page {

    private final ProductService productService;

    private final UserService userService;
    private Principal principal = () -> null;

    @GetMapping("/")
    public String welcome(
            @RequestParam(name = "name", required = false) String name,
            Model model
    ) {
        model.addAttribute("products", productService.findByName(name));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable String id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("images", product.getImages());
        return "productsinfo";
    }

    @PostMapping("/create")
    public String createProduct(
            @ModelAttribute ProductDto productDto,
            @RequestParam("file1") MultipartFile file1
    ) {
        productService.createProduct(principal, productDto, file1);
        return "redirect:/";
    }

    @PostMapping("/remove/{productId}")
    public String deleteById(
            @PathVariable("productId") String productId
    ) {
        productService.deleteById(productId);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/authentication/login")
    public String login(AuthenticationRequest request, Model model) {
        System.out.println("Im in Page");
        userService.authenticate(request);
        principal = request::getUserNickname;
//        return welcome(null,principal,model);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registration")
    public String register(
            RegisterRequest request
    ) {
        userService.register(request);
        return "redirect:/login";
    }
}

package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ErrorDto;
import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.service.interf.ProductService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "ProductController", description = "class for processing requests for a product")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Finds the product",
            description = "Finds the product by id and return product",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = Product.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/id")
    public Product getProductById(
            @Parameter(
                    description = "ID of product to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String id
    ) {
        return productService.findById(id);
    }

    @Operation(summary = "Finds the products",
            description = "Finds the products by name and return productsDto",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(
                                    description = "return List of ProductDto",
                                    implementation = ProductDto.class
                            ),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/name")
    public List<ProductDto> getProductByName(
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Search for a product by name")
            @NotNull @Size(min = 1, max = 44) @RequestParam String name
    ) {
        return productService.findByName(name);
    }

    @Operation(summary = "Finds the All products",
            description = "Finds the all products",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(
                                            description = "return List of ProductDto",
                                            implementation = ProductDto.class
                                    ),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Finds the products",
            description = "Finds the all products by category",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(
                                            description = "return List of ProductDto",
                                            implementation = ProductDto.class
                                    ),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/allByCategory/")
    public List<ProductDto> getAllProductsByCategory(
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Finds all products if the category is valid")
            @NotNull @Size(min = 1, max = 44) @RequestParam String category
    ) {
        return productService.getAllProductsByCategory(category);
    }

    @Operation(summary = "Finds the products",
            description = "Finds the all products by brand",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(
                                            description = "return List of ProductDto",
                                            implementation = ProductDto.class
                                    ),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/allByBrand/")
    public List<ProductDto> getAllProductsByBrand(
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Finds all products if the brand is valid")
            @NotNull @Size(min = 1, max = 44) @RequestParam String brand
    ) {
        return productService.getAllProductsByBrand(brand);
    }

    @Operation(summary = "Finds the products",
            description = "Finds the all products by price from to",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(
                                            description = "return List of ProductDto",
                                            implementation = ProductDto.class
                                    ),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/allByPrice/search")
    public List<ProductDto> searchProductsByPriceRange(
            @Schema(minimum = "0.01", maximum = "999999.99",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Finds all products from price")
            @NotNull
            @Positive
            @DecimalMin(value = "0.01", message = "Price should be greater than 0")
            @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00")
            @RequestParam BigDecimal minPrice,
            @Schema(minimum = "0.01", maximum = "999999.99",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Finds all products to price")
            @NotNull
            @Positive
            @DecimalMin(value = "0.01", message = "Price should be greater than 0")
            @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00")
            @RequestParam BigDecimal maxPrice
    ) {
        return productService.searchProductsByPriceRange(minPrice, maxPrice);
    }

    @Operation(summary = "Finds the products",
            description = "Finds the all products by category and brand",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = ProductDto.class
                                    ),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/allByCategoryBrand/search")
    public List<ProductDto> searchProductsByCategoryBrand(
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Finds all products if the category is valid")
            @NotNull @Size(min = 1, max = 44) @RequestParam String category,
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Finds all products if the brand is valid")
            @NotNull @Size(min = 1, max = 44) @RequestParam String brand
    ) {
        return productService.searchProductsByCategoryBrand(category, brand);
    }

    @Operation(summary = "Saves the Product",
            description = "Stores ProductDto and returns ProductDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ProductDto data for saving",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = ProductDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not save because user Nick Name is not valid",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PostMapping("/create")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @Operation(summary = "Change the name product",
            description = "Changing the name of a product by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PostMapping("/update/name/{productId}and{productName}")
    public void updateProductNameWithId(
            @Parameter(
                    description = "ID of product to be retrieved",
                    required = true)
            @NotNull @IdChecker @PathVariable("productId") String productId,
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Change product name if length of parameter its valid")
            @NotNull @Size(min = 1, max = 44) @PathVariable("productName") String name
    ) {
        productService.updateProductNameWithId(productId, name);
    }

    @Operation(summary = "Change the description product",
            description = "Changing the description of a product by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PostMapping("/update/description/{productId}and{productDescription}")
    public void updateProductDescriptionWithId(
            @Parameter(
                    description = "ID of product to be retrieved",
                    required = true)
            @NotNull @IdChecker @PathVariable("productId") String productId,
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Change product description if length of parameter its valid")
            @NotNull
            @Size(min = 1, max = 254)
            @PathVariable("productDescription") String description
    ) {
        productService.updateProductDescriptionWithId(productId, description);
    }

    @Operation(summary = "Change the price product",
            description = "Changing the price of a product by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PostMapping("/update/price/{productId}and{productPrice}")
    public void updateProductPriceWithId(
            @Parameter(
                    description = "ID of product to be retrieved",
                    required = true)
            @NotNull @IdChecker @PathVariable("productId") String productId,
            @Schema(minimum = "0.01", maximum = "999999.99",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Change product price")
            @Positive
            @DecimalMin(value = "0.01", message = "Price should be greater than 0")
            @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00")
            @PathVariable("productPrice") String price
    ) {
        productService.updateProductPriceWithId(productId, price);
    }

    @Operation(summary = "Change the category product",
            description = "Changing the category of a product by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PostMapping("/update/category/{productId}and{productCategory}")
    public void updateProductCategoryWithId(
            @Parameter(
                    description = "ID of product to be retrieved",
                    required = true)
            @NotNull @IdChecker @PathVariable("productId") String productId,
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Change the product category")
            @NotNull @Size(min = 1, max = 44) @PathVariable("productCategory") String category
    ) {
        productService.updateProductCategoryWithId(productId, category);
    }

    @Operation(summary = "Change the brand product",
            description = "Changing the brand of a product by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PostMapping("/update/brand/{productId}and{productBrand}")
    public void updateProductBrandWithId(
            @Parameter(
                    description = "ID of product to be retrieved",
                    required = true)
            @NotNull @IdChecker @PathVariable("productId") String productId,
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "change the product brand")
            @NotNull @Size(min = 1, max = 44) @PathVariable("productBrand") String brand
    ) {
        productService.updateProductBrandWithId(productId, brand);
    }

    @Operation(summary = "Delete Product",
            description = "Deletes a product from database by given id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content
                                    (schema = @Schema(
                                            defaultValue = "Product with ID \" + productId + \" has been deleted"),
                                            mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Product not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> deleteById(
            @Parameter(
                    description = "ID of product to be retrieved",
                    required = true)
            @NotNull @IdChecker @PathVariable("productId") String productId
    ) {
        productService.deleteById(productId);
        return ResponseEntity.ok("Product with ID " + productId + " has been deleted");
    }
}

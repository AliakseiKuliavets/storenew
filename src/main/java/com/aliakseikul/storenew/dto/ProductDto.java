package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Tag(name = "ProductDto", description = "Product DTO have fields " +
        "name, price, description, category, brand, nickName who add product")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    String productId;

    @Schema(minLength = 1, maxLength = 44, description = "Name of product")
    @NotNull(message = "Product name shouldn't be null")
    @Size(min = 1, max = 44, message = "Product should be not null and from 1 to 44 symbols")
    String productName;

    @Schema(minimum = "0.01", maximum = "999999.99", description = "Price of the product")
    @Positive(message = "Price must be greater than 0")
    @NotNull(message = "Price shouldn't be null")
    @DecimalMin(value = "0.01", message = "Price should be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00")
    private BigDecimal productPrice;

    @Schema(minLength = 1, maxLength = 254, description = "Description of the product")
    @NotNull(message = "Description shouldn't be null")
    @Size(min = 1, max = 254, message = "Description should be not null and from 1 to 254 symbols")
    String productDescription;

    @Schema(minLength = 1, maxLength = 44, description = "Product category of enum ProductCategory")
    @NotNull(message = "Category shouldn't be null")
    @Size(min = 1, max = 44, message = "Category should be not null and from 1 to 44 symbols")
    ProductCategory productCategory;

    @Schema(minLength = 1, maxLength = 44, description = "Product brand of enum ProductBrand")
    @NotNull(message = "Brand shouldn't be null")
    @Size(min = 1, max = 44, message = "Brand should be not null and from 1 to 44 symbols")
    ProductBrand productBrand;

    @Schema(minLength = 1, maxLength = 44, description = "Product userNickName who added this product")
    @NotNull(message = "Nickname shouldn't be null")
    @Size(min = 1, max = 44, message = "Nickname should be not null and from 1 to 44 symbols")
    String userNickname;
}

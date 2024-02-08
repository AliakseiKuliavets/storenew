package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    String productId;

    @NotNull(message = "Product name shouldn't be null")
    @Size(min = 1, max = 44, message = "Product should be not null and from 1 to 44 symbols")
    String productName;

    @Positive(message = "Price must be greater than 0")
    @NotNull(message = "Price shouldn't be null")
    @DecimalMin(value = "0.01", message = "Price should be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price should be less than 1 000 000.00")
    private BigDecimal productPrice;

    @NotNull(message = "Description shouldn't be null")
    @Size(min = 1, max = 254, message = "Description should be not null and from 1 to 254 symbols")
    String productDescription;

    @NotNull(message = "Category shouldn't be null")
    @Size(min = 1, max = 44, message = "Category should be not null and from 1 to 44 symbols")
    ProductCategory productCategory;

    @NotNull(message = "Brand shouldn't be null")
    @Size(min = 1, max = 44, message = "Brand should be not null and from 1 to 44 symbols")
    ProductBrand productBrand;

    @NotNull(message = "Nickname shouldn't be null")
    @Size(min = 1, max = 44, message = "Nickname should be not null and from 1 to 44 symbols")
    String userNickname;
}

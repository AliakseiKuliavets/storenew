package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    String productName;
    double productPrice;
    String productDescription;
    ProductCategory productCategory;
    ProductBrand productBrand;
    UUID placedByUserId;
}

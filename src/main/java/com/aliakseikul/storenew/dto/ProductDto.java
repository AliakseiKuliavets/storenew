package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    String productName;
    BigDecimal productPrice;
    String productDescription;
    ProductCategory productCategory;
    ProductBrand productBrand;

    String userNickname;
}

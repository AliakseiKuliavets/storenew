package com.aliakseikul.storenew;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String productName;
    private double productPrice;
    private String productDescription;
    private ProductCategory productCategory;
    private ProductBrand productBrand;
    private String placedByUser;

}

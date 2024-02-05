package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EnumController {

    @GetMapping("/api/enum/allCategory")
    public List<String> getProductCategories(){
        return ProductCategory.getProductCategoryList();
    }

    @GetMapping("/api/enum/allBrand")
    public List<String> getProductBrand(){
        return ProductBrand.getProductBrandList();
    }
}

package com.aliakseikul.storenew.entity.enums;

import java.util.Arrays;
import java.util.List;

public enum ProductCategory {
    ELECTRONICS,
    SPORTS,
    OTHER;

    public static List<String> getProductCategoryList() {
        return Arrays.stream(values())
                .map(Enum::name)
                .toList();
    }
}

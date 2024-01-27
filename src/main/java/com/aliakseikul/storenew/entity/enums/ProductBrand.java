package com.aliakseikul.storenew.entity.enums;

import java.util.Arrays;
import java.util.List;

public enum ProductBrand {
    APPLE,
    SAMSUNG,
    PHILIPS,
    ASUS,
    LG,
    NIKE,
    HASBRO,
    BLACK_AND_DECKER,
    ADIDAS,
    DELL;

    public static List<String> getProductBrandList() {
        return Arrays.stream(values())
                .map(Enum::name)
                .toList();
    }
}

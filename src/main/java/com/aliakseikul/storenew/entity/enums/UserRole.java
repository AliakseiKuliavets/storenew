package com.aliakseikul.storenew.entity.enums;

import java.util.Arrays;
import java.util.List;

public enum UserRole {
    USER,
    ADMIN;

    public static List<String> getListRole() {
        return Arrays.stream(values())
                .map(Enum::name)
                .toList();
    }
}

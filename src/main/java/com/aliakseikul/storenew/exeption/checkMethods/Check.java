package com.aliakseikul.storenew.exeption.checkMethods;

public class Check {

    private Check() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean valueNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean checkIdLength(String productId) {
        return productId.length() != 36 || valueNullOrEmpty(productId);
    }

    public static boolean checkNumber(String number) {
        char[] chars = number.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}

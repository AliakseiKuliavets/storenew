package com.aliakseikul.storenew.exception.checkMethods;

public class Check {

    private Check() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean valueNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean checkString45Length(String s) {
        return !(s.length() > 1 && s.length() < 45);
    }

    public static boolean checkIdLength(String value) {
        return value.length() != 36;
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

package com.aliakseikul.storenew.exeption.checkMethods;

import com.aliakseikul.storenew.exeption.exeptions.ProductNotFoundException;
import com.aliakseikul.storenew.exeption.exeptions.StringIsNullExceptions;
import com.aliakseikul.storenew.exeption.message.ErrorMessage;

public class Check {

    private Check() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean valueNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean checkIdLength(String value) {
        return value.length() != 36 || valueNullOrEmpty(value);
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
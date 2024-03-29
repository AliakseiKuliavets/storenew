package com.aliakseikul.storenew.exception.message;

public class ErrorMessage {


    private ErrorMessage() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ROLE_NOT_FOUND = "User role not found";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_WITH_NAME = "The nickname is already taken";
    public static final String ORDER_NUMBER_NOT_FOUND = "Order number not found";
    public static final String DELIVERY_NOT_FOUND = "Delivery not found";
    public static final String CATEGORY_NOT_FOUND = "Category not found or poorly written";
    public static final String BRAND_NOT_FOUND = "Brand not found or poorly written";
    public static final String NUMBER_ERROR = "Number is wrong, contains not digits";
    public static final String NULL_OR_EMPTY = "String null or empty";
    public static final String WRONG_EMAIL = "Email is wrong";
}

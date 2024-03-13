package com.aliakseikul.storenew.validation.constraint;


import com.aliakseikul.storenew.validation.interf.IdChecker;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdAnnotationChecker implements ConstraintValidator<IdChecker, String> {

    private static final String TEMPLATE =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

    @Override
    public void initialize(IdChecker constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches(TEMPLATE);
    }
}

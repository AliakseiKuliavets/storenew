package com.aliakseikul.storenew.validation.constraint;

import com.aliakseikul.storenew.validation.interf.Str45LengthCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class StringAnnotationChecker implements ConstraintValidator<Str45LengthCheck, String> {

    @Override
    public void initialize(Str45LengthCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .filter(el -> !(el.isBlank()))
                .map(el -> el.length() < 45)
                .orElse(false);
    }
}

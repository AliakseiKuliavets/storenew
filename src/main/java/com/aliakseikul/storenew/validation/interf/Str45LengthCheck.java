package com.aliakseikul.storenew.validation.interf;

import com.aliakseikul.storenew.validation.constraint.StringAnnotationChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StringAnnotationChecker.class})
public @interface Str45LengthCheck {

    String message() default "String length wrong";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

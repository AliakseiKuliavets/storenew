package com.aliakseikul.storenew.validation.interf;


import com.aliakseikul.storenew.validation.constraint.IdAnnotationChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdAnnotationChecker.class})
public @interface IdChecker {

    String message() default "It`s not UUID format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

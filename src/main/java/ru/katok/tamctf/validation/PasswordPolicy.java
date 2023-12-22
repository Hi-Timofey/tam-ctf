package ru.katok.tamctf.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordPolicyValidator.class)
@Documented
public @interface PasswordPolicy {

    String message() default "Password doesn't match password policy (at least 8 characters)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

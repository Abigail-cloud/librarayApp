package com.abigail.libraryapp.validation.password;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * password-matches: 2 new annotation interfaces that define custom validation rules using @Constraint and @Target annotations.
 * A group is a way to validate a subset of fields in a request.
 * This is useful when you want to validate different sets of fields based on certain conditions.
 * note--group is associated to validation
 */
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {
    String password();

    String confirmPassword();

    String message() default "Passwords must match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PasswordMatches[] value();
    }
}

package com.abigail.libraryapp.validation.password;

import com.abigail.libraryapp.validation.password.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * check if string contains at least one digit, one lowercase letter,
 * one uppercase letter, one special character and 8 characters long
 * We use String matches() function with regular expression for a strong password:
 * returns
 * ^: the start of the string
 * (?=.*\d): at least one digit
 * (?=.*[a-z]): at least one lowercase letter
 * (?=.*[A-Z]): at least one uppercase letter
 * (?=.*[@#$%^&+=!*()]): at least one special character
 * .{8,}: at least 8 characters long
 * $: the end of the string
 * continuation to create custom validator class for validating multiple fields together.
 */

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$");
    }
}

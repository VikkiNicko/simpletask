package com.simpletask.validation;


import com.simpletask.security.auth.RegisterRequest;
import com.simpletask.validation.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegisterRequest request = (RegisterRequest) o;
        return request.getPassword().equals(request.getConfirmPassword());
    }
}

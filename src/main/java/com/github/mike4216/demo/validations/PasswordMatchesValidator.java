package com.github.mike4216.demo.validations;

import com.github.mike4216.demo.annotations.PasswordMatches;
import com.github.mike4216.demo.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest userSignUpRequest = (SignUpRequest) obj;
        return userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword());
    }

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}

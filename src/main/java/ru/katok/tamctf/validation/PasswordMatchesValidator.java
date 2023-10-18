package ru.katok.tamctf.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.katok.tamctf.api.dto.SignUpDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final SignUpDto user = (SignUpDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
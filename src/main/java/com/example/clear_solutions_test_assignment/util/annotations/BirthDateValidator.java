package com.example.clear_solutions_test_assignment.util.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;

public final class BirthDateValidator implements ConstraintValidator<BirthDate, TemporalAccessor> {

    @Value("${min-age}")
    private String age;

    @Override
    public boolean isValid(TemporalAccessor value, ConstraintValidatorContext context) {
        if (value != null) {
            LocalDate date = LocalDate.parse(value.toString());
            long minAge = Long.parseLong(age);
            LocalDate minYear = LocalDate.now().minusYears(minAge);
            return date.isBefore(minYear);
        }
        return false;
    }
}

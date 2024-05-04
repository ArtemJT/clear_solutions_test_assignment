package com.example.clear_solutions_test_assignment.util.aspect;

import com.example.clear_solutions_test_assignment.exception.DateRangeException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Component
public record DateRangeValidatorAspect() {

    @Pointcut("execution(public * com.example.clear_solutions_test_assignment.controller.UserController.getAllByBirthDateRange(..))")
    public void callAtGetAllByBirthDateRangeMethod() {
    }

    @Before("callAtGetAllByBirthDateRangeMethod()")
    public void checkBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        LocalDate from = (LocalDate) args[0];
        LocalDate to = (LocalDate) args[1];
        if (from != null && to != null && (from.isEqual(to) || from.isAfter(to))) {
            throw new DateRangeException("Date 'FROM' must be earlier than 'TO'");
        }
    }
}

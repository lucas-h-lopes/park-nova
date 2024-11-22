package api_gestao_estacionamento.projeto.web.constraint.validator;

import api_gestao_estacionamento.projeto.web.constraint.MaxAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MaxAgeValidator implements ConstraintValidator<MaxAge, LocalDate> {

    private int age;

    @Override
    public void initialize(MaxAge constraintAnnotation) {
        this.age = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return true;
        }
        return Period.between(localDate, LocalDate.now()).getYears() <= age;
    }
}

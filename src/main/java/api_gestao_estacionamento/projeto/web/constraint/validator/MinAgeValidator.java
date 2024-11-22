package api_gestao_estacionamento.projeto.web.constraint.validator;

import api_gestao_estacionamento.projeto.web.constraint.MinAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {
//<A,T> -> anotação, tipo do campo

    private int minAge;

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.minAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return true;
        }
        return Period.between(birthDate, LocalDate.now()).getYears() >= minAge;
    }
}

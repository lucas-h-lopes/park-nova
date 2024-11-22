package api_gestao_estacionamento.projeto.web.constraint.validator;

import api_gestao_estacionamento.projeto.web.constraint.Data;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataValidator implements ConstraintValidator<Data, LocalDate> {

    String pattern;

    @Override
    public void initialize(Data constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalDateTime.parse(getDateFromPattern(pattern), DateTimeFormatter.ofPattern(pattern));
            return true;
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    private String getDateFromPattern(String pattern) {
        String delimiter = "-";
        String[] arr = pattern.split(delimiter);

        if (arr.length == 1 && arr[0].equals(pattern)) {
            arr = pattern.split("/");
            delimiter = "/";
            if (arr.length == 1 && arr[0].equals(pattern)) {
                arr = pattern.split(".");
                delimiter = ".";
                if (arr.length == 1 && arr[0].equals(pattern)) {
                    arr = pattern.split(" ");
                    delimiter = " ";
                    if (arr.length == 1 && arr[0].equals(pattern)) {
                        return null;
                    }
                }
            }
        }
        if (arr.length != 3) {
            return null;
        }
        if (arr[0].length() == 4) {
            return "2004".concat(delimiter).concat("10").concat("11");
        }
        return "10".concat(delimiter).concat("10").concat("1000");
    }
}

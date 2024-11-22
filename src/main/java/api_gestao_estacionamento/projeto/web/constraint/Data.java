package api_gestao_estacionamento.projeto.web.constraint;

import api_gestao_estacionamento.projeto.web.constraint.validator.DataValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DataValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {
    String pattern() default "dd/MM/yyyy";
    String message() default "A data não está no padrão especificado '{pattern}'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package api_gestao_estacionamento.projeto.web.constraint;

import api_gestao_estacionamento.projeto.web.constraint.validator.MinAgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MinAgeValidator.class) //classe que implementa a lógica de validação
@Target(ElementType.FIELD) //onde a anotação poderá ser utilizada
@Retention(RetentionPolicy.RUNTIME) //para especificar quando será utilizada
public @interface MinAge {
    String message() default "O usuário precisa possuir no mínimo {value} anos de idade."; //parametro message

    Class<?>[] groups() default {}; //parametro groups -> obrigatório

    Class<? extends Payload>[] payload() default {}; // parametro payload -> obrigatorio

    int value(); // parametro para realizar o calculo
}

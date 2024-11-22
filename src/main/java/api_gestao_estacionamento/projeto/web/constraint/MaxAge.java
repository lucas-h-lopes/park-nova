package api_gestao_estacionamento.projeto.web.constraint;

import api_gestao_estacionamento.projeto.web.constraint.validator.MaxAgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MaxAgeValidator.class) //classe que tem a logica de validação
@Target(ElementType.FIELD) // onde a anotação pode ser utilizada
@Retention(RetentionPolicy.RUNTIME) // quando vai ser "disparada"
public @interface MaxAge {
    String message() default "O usuário precisa possuir no máximo {value} anos de idade."; //parametro mensagem

    int value(); //parametro valor, será utilizado na logica de validação

    Class<?>[] groups() default {}; // parametro groups -> obrigatório declarar

    Class<? extends Payload>[] payload() default {}; // parametro payload -> obrigatorio declarar
}

package api_gestao_estacionamento.projeto.util;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.exception.InvalidTemplateException;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.service.mail.templates.impl.WelcomeTemplate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateUtils {

    public static EmailTemplate getTemplate(String template, User user, String activationToken){
        return switch(template.toUpperCase().trim()){
            case "WELCOME" -> new WelcomeTemplate(user, activationToken);
            default -> throw new InvalidTemplateException(String.format("O template '%s' é inválido", template));
        };
    }
}

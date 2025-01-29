package api_gestao_estacionamento.projeto.util;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.exception.InactiveAccountException;
import api_gestao_estacionamento.projeto.service.exception.InvalidTemplateException;
import api_gestao_estacionamento.projeto.service.exception.UserIsAlreadyActiveException;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.service.mail.templates.enums.EmailTemplateEnum;
import api_gestao_estacionamento.projeto.service.mail.templates.impl.FidelityTemplate;
import api_gestao_estacionamento.projeto.service.mail.templates.impl.WelcomeTemplate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateUtils {

    public static EmailTemplate getTemplate(EmailTemplateEnum template, User user) {
        return switch (template) {
            case WELCOME -> new WelcomeTemplate(user, user.getActivationToken());
            case FIDELITY -> new FidelityTemplate(user);
        };
    }

    public static void validateTemplate(User user, String template) {
        try {
            EmailTemplateEnum enumTemplate = EmailTemplateEnum.valueOf(template.toUpperCase());
            if (enumTemplate == EmailTemplateEnum.WELCOME && user.isActive()) {
                throw new UserIsAlreadyActiveException("O usuário já está ativo no sistema");
            }
            if (enumTemplate != EmailTemplateEnum.WELCOME && !user.isActive()) {
                throw new InactiveAccountException("A conta precisa estar ativa para enviar esse tipo de e-mail");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidTemplateException(String.format("O template '%s' é inválido", template));
        }
    }

    public static String loadHtml(String body) {
        return " <html>" +
                "                <head>" +
                "                    <style>" +
                "                        a {" +
                "                            text-decoration: none;" +
                "                            color: #D2FF96;" +
                "                            font-weight: bold;" +
                "                        }" +
                "                    </style>" +
                "                </head>" +
                "                <body>" +
                "<table role=\"presentation\" width=\"75%\" height=\"100%\" style=\"background-color: #F2F2F2;margin-left: auto; margin-right: auto;\">" +
                "                        <tr>" +
                "                            <td style=\"padding: 20px;\">" + body +
                "                            </td>" +
                "                        </tr>" +
                "                    </table>" +
                "                </body>" +
                "                </html>";
    }
}

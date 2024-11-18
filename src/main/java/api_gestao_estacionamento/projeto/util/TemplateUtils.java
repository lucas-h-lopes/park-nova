package api_gestao_estacionamento.projeto.util;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.exception.InvalidTemplateException;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.service.mail.templates.impl.WelcomeTemplate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateUtils {

    private static String[] templates = new String[]{"welcome"};

    public static EmailTemplate getTemplate(String template, User user, String activationToken) {
        return switch (template.toUpperCase().trim()) {
            case "WELCOME" -> new WelcomeTemplate(user, activationToken);
            default -> throw new InvalidTemplateException(String.format("O template '%s' é inválido", template));
        };
    }

    public static boolean isTemplateValid(String template) {
        if (!Arrays.asList(templates).contains(template.toLowerCase())) {
            throw new InvalidTemplateException(String.format("O template '%s' é inválido", template));
        }
        return true;
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

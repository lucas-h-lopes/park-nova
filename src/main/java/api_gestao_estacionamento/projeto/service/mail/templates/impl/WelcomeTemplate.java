package api_gestao_estacionamento.projeto.service.mail.templates.impl;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.util.TemplateUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class WelcomeTemplate extends EmailTemplate {

    private final static String body = "<h2>Olá, %s!</h2>" +
            "                   <p>Agradecemos por se registrar no <i>Park Nova</i>, a solução inteligente e inovadora para a gestão de estacionamentos.</p> " +
            "                    Para concluir seu cadastro e ativar sua conta, por favor, clique no link abaixo:" +
            "                    <br/><br/><a href=\"%s\">Ativar Conta</a>" +
            "                    <br/><br/>Se você não realizou esse cadastro, por favor, ignore este e-mail." +
            "                    <br/>Estamos à disposição para qualquer dúvida ou assistência." +
            "                    <br/><br/>Atenciosamente," +
            "                    <br/><b>Equipe Park Nova</b>";


    public WelcomeTemplate(User user, String activationToken) {
        super("Ativação de Conta!", TemplateUtils.loadHtml(String.format(body, getFirstName(user.getName()), String.format("http://localhost:8080/api/v1/users/activate-account/%d?token=%s", user.getId(), activationToken))));
    }

    private static String getFirstName(String fullname) {
        fullname = fullname.trim();
        return Arrays.stream(fullname.split(" "))
                .findFirst()
                .get();
    }
}

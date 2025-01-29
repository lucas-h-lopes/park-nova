package api_gestao_estacionamento.projeto.service.mail.templates.impl;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.util.TemplateUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class WelcomeTemplate implements EmailTemplate {

    private final static String body = "<h2>Olá, %s!</h2>" +
            "                   <p>Agradecemos por se registrar no <i>Park Nova</i>, a solução inteligente e inovadora para a gestão de estacionamentos.</p> " +
            "                    Para concluir seu cadastro e ativar sua conta, por favor, clique no link abaixo:" +
            "                    <br/><br/><a href=\"%s\">Ativar Conta</a>" +
            "                    <br/><br/>Se você não realizou esse cadastro, por favor, ignore este e-mail." +
            "                    <br/>Estamos à disposição para qualquer dúvida ou assistência." +
            "                    <br/><br/>Atenciosamente," +
            "                    <br/><b>Equipe Park Nova</b>";


    private User user;
    private String userToken;

    public WelcomeTemplate(User user, String userToken){
        this.user = user;
        setUserToken(userToken);
    }


    @Override
    public String getSubject() {
        return "Ativação de conta!";
    }

    @Override
    public String getText() {
        return TemplateUtils.loadHtml(String.format(body, getFirstName(user.getName()), String.format("http://localhost:8080/api/v1/users/activate-account/%d?token=%s", user.getId(), userToken)));
    }

    @Override
    public void setUserToken(String token) {
        this.userToken = token;
    }

}

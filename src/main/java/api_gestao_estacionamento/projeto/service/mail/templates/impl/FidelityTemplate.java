package api_gestao_estacionamento.projeto.service.mail.templates.impl;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.util.TemplateUtils;

public class FidelityTemplate implements EmailTemplate {

    private final static String body = "<h2>Olá, %s!</h2>" +
            "                   <p>Parabéns! Como um cliente fiel do <i>Park Nova</i>, temos o prazer de recompensá-lo com um benefício exclusivo. A partir de hoje, você receberá um desconto de 5%% em todos os seus check-ins!</p>" +
            "                    Aproveite essa vantagem em todas as suas futuras visitas ao nosso estacionamento." +
            "                    <br/><br/>Se você tiver qualquer dúvida ou precisar de assistência, estamos à disposição para ajudá-lo." +
            "                    <br/><br/>Atenciosamente," +
            "                    <br/><b>Equipe Park Nova</b>";

    private User user;

    public FidelityTemplate(User user) {
        this.user = user;
    }

    @Override
    public String getSubject() {
        return "Desconto de 5% garantido para seus próximos check-ins! 🎉";
    }

    @Override
    public String getText() {
        return TemplateUtils
                .loadHtml(String.format(body, getFirstName(user.getName())));
    }

    @Override
    public void setUserToken(String token) {}
}

package api_gestao_estacionamento.projeto.service.mail.templates.impl;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Getter
public class WelcomeTemplate extends EmailTemplate {

    public WelcomeTemplate(User user, String activationToken) {
        super("Ativação de Conta!", String.format("Olá, %s!\n\nObrigado por se cadastrar no Park Nova, a solução inteligente para a gestão de estacionamentos.\nPara completar o processo de registro, você precisa ativar sua conta.\n\nPor favor, clique no link abaixo para ativar sua conta e começar a utilizar todos os recursos disponíveis:\n\nhttp://localhost:8080/api/v1/users/activate-account/%d?token=%s\n\nSe você não se cadastrou no Park Nova, pode ignorar este email.\n\nEstamos à disposição para qualquer dúvida!\n\nAtenciosamente,\n\nEquipe Park Nova", getFirstName(user.getName()), user.getId(), activationToken));
    }

    private static String getFirstName(String fullname) {
        fullname = fullname.trim();
        return Arrays.stream(fullname.split(" "))
                .findFirst()
                .get();
    }
}

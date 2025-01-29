package api_gestao_estacionamento.projeto.service.mail;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.util.ActivationTokenUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Setter
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private MimeMessage prepareMimeMessage(String recipient, EmailTemplate emailTemplate) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject(emailTemplate.getSubject());
            helper.setText(emailTemplate.getText(), true);

            return mimeMessage;
        } catch (Exception e) {
            log.info("Falha no envio de email - {}", e.getLocalizedMessage());
        }
        return null;
    }

    @Async
    @Transactional
    public void sendMail(User user, EmailTemplate template) {
        if ((Duration.between(user.getLastModifiedAt(), LocalDateTime.now()).toHours() > 24) && (!user.isActive())) {
            String token = ActivationTokenUtils.generateActivationToken();
            user.setActivationToken(token);
            template.setUserToken(token);
        }

        MimeMessage message = prepareMimeMessage(user.getUsername(), template);
        if (message != null) {
            try {
                javaMailSender.send(message);
            } catch (Exception e) {
                log.info("Não foi possível enviar o email");
            }
        }
    }

}


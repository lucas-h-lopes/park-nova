package api_gestao_estacionamento.projeto.service.mail;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.UserService;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.util.ActivationTokenUtils;
import api_gestao_estacionamento.projeto.util.TemplateUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
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
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private final UserService userService;

    private MimeMessage prepareMimeMessage(String recipient, EmailTemplate emailTemplate) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject(emailTemplate.getSubject());
            helper.setText(emailTemplate.getText(), true);

            return mimeMessage;
        } catch (MailException | MessagingException e) {
            log.info("Falha no envio de email - {}", e.getLocalizedMessage());
        }
        return null;
    }

    @Async
    @Transactional
    public void sendMail(String username, String template) {
        User user = userService.loadUserByUsername(username, true);
        if ((Duration.between(user.getLastModifiedAt(), LocalDateTime.now()).toHours() > 24) && (!user.isActive())) {
            user.setActivationToken(ActivationTokenUtils.generateActivationToken());
        }
        EmailTemplate emailTemplate = TemplateUtils.getTemplate(template, user, user.getActivationToken());
        MimeMessage message = prepareMimeMessage(username, emailTemplate);
        if (message != null && !user.isActive()) {
            try {
                javaMailSender.send(message);
            } catch (Exception e) {
                log.info("Não foi possível enviar o email");
            }
        }
    }
}


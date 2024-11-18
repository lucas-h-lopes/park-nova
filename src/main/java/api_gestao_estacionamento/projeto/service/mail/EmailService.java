package api_gestao_estacionamento.projeto.service.mail;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.UserService;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import api_gestao_estacionamento.projeto.util.ActivationTokenUtils;
import api_gestao_estacionamento.projeto.util.TemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private final UserService userService;

    private SimpleMailMessage prepareEmail(String recipient, EmailTemplate emailTemplate) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setSubject(emailTemplate.getSubject());
            simpleMailMessage.setText(emailTemplate.getText());
            simpleMailMessage.setTo(recipient);

            return simpleMailMessage;
        } catch (MailException e) {
            log.info("Falha no envio de email - {}", e.getLocalizedMessage());
        }
        return null;
    }

    @Async
    @Transactional
    public void sendMail(String username, String template) {
            User user = userService.loadUserByUsername(username, true);
            String activationToken = ActivationTokenUtils.generateActivationToken();

            EmailTemplate emailTemplate = TemplateUtils.getTemplate(template, user, activationToken);
            SimpleMailMessage message = prepareEmail(username, emailTemplate);
            if (message != null && !user.isActive()) {
                javaMailSender.send(message);
                user.setActivationToken(activationToken);
            }
        }
    }


package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.builder.UserBuilder;
import api_gestao_estacionamento.projeto.matcher.MyMatchers;
import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.mail.EmailService;
import api_gestao_estacionamento.projeto.service.mail.templates.EmailTemplate;
import jakarta.mail.internet.MimeMessage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @InjectMocks
    public EmailService service;

    @Mock
    public JavaMailSender javaMailSender;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service.setSender("parknova.noreply@gmail.com");
    }

    private EmailTemplate mockEmailTemplate() {
        EmailTemplate emailTemplate = Mockito.mock(EmailTemplate.class);
        Mockito.when(emailTemplate.getSubject()).thenReturn("Some subject");
        Mockito.when(emailTemplate.getText()).thenReturn("Some text");
        return emailTemplate;
    }

    @Test
    public void shouldSendEmailToNewUserAndKeepActivationToken() {
        //scenario
        User user = UserBuilder.createInactiveUser().get();
        String originalToken = user.getActivationToken();

        EmailTemplate emailTemplate = mockEmailTemplate();
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);

        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        //action
        service.sendMail(user, emailTemplate);

        //verify
        Mockito.verify(javaMailSender, Mockito.atMost(1)).createMimeMessage();
        Mockito.verify(javaMailSender, Mockito.atMost(1)).send(mimeMessage);
        error.checkThat(originalToken, MyMatchers.equalString(user.getActivationToken()));
    }

    @Test
    public void shouldSendEmailToOldInactiveUserAndChangeActivationToken() {
        //scenario
        User user = UserBuilder.createOldInactiveUser().get();
        String originalToken = user.getActivationToken();

        EmailTemplate emailTemplate = mockEmailTemplate();
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);

        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        //action
        service.sendMail(user, emailTemplate);

        //verify
        Mockito.verify(javaMailSender, Mockito.times(1)).createMimeMessage();
        Mockito.verify(javaMailSender, Mockito.times(1)).send(mimeMessage);
        error.checkThat(originalToken, MyMatchers.differentString(user.getActivationToken()));
    }

    @Test
    public void shouldSendEmailToOldActiveUserAndKeepActivationToken() {
        //scenario
        User user = UserBuilder.createOldActiveUser().get();
        String originalToken = user.getActivationToken();

        EmailTemplate emailTemplate = mockEmailTemplate();
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);

        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        //action
        service.sendMail(user, emailTemplate);

        //verify
        Mockito.verify(javaMailSender, Mockito.times(1)).createMimeMessage();
        Mockito.verify(javaMailSender, Mockito.times(1)).send(mimeMessage);
        error.checkThat(originalToken, MyMatchers.equalString(user.getActivationToken()));
    }

    @Test
    public void shouldNotSendEmailWhenExceptionIsThrown() {
        //scenario
        User user = UserBuilder.createInvalidUser().get();

        EmailTemplate emailTemplate = Mockito.mock(EmailTemplate.class);
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);

        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        //action
        service.sendMail(user, emailTemplate);

        //verify
        Mockito.verify(javaMailSender, Mockito.times(1)).createMimeMessage();
        Mockito.verify(javaMailSender, Mockito.never()).send(Mockito.any(MimeMessage.class));
    }
}

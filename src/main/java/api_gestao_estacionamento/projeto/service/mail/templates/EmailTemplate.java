package api_gestao_estacionamento.projeto.service.mail.templates;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class EmailTemplate {
    protected String subject;
    protected String text;
}

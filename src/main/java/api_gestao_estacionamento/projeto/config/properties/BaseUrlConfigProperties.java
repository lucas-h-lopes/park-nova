package api_gestao_estacionamento.projeto.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "endpoint.base-url")
@Getter @Setter
public class BaseUrlConfigProperties {

    private String user;
    private String login;
}

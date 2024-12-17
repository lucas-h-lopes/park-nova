package api_gestao_estacionamento.projeto.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter @Setter
public class JwtConfigProperties {

    private String secret;
    private String authorization;
    private String bearer;
}

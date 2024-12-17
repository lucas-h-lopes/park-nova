package api_gestao_estacionamento.projeto.config;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.util.ActivationTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;

@Configuration
public class UserBeanConfig {

    @Bean(name = "admin")
    @Primary
    public User admin(){
        User user = new User();
        user.setName("ADMIN");
        user.setUsername("admin@email.com");
        user.setPassword("123456");
        user.setRole(User.Role.ROLE_ADMIN);
        user.setCreatedBy("dbSeeding");
        user.setLastModifiedBy("dbSeeding");
        user.setCreatedAt(LocalDateTime.now());
        user.setLastModifiedAt(LocalDateTime.now());
        user.setActive(true);
        user.setActivationToken(ActivationTokenUtils.generateActivationToken());

        return user;
    }

    @Bean(name = "client")
    public User client(){
        User user = new User();
        user.setName("TEST CLIENT");
        user.setUsername("client@email.com");
        user.setPassword("123456");
        user.setRole(User.Role.ROLE_CLIENT);
        user.setCreatedBy("dbSeeding");
        user.setLastModifiedBy("dbSeeding");
        user.setCreatedAt(LocalDateTime.now());
        user.setLastModifiedAt(LocalDateTime.now());
        user.setActive(true);
        user.setActivationToken(ActivationTokenUtils.generateActivationToken());

        return user;
    }
}

package api_gestao_estacionamento.projeto.config;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.repository.UserRepository;
import api_gestao_estacionamento.projeto.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DbSeedingConfig {

    private final UserRepository userRepository;

    private final UserService userService;

    private final User admin;

    private final User client;

    public DbSeedingConfig(UserRepository userRepository, UserService userService, User admin, @Qualifier("client") User client) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.admin = admin;
        this.client = client;
    }

    @PostConstruct
    public void insertAdminIntoDb() {
        if (userRepository.loadUserByUsername("admin@email.com").isEmpty()) {
            userService.insert(admin);
        }
    }

    @PostConstruct
    public void insertClientIntoDb() {
        if (userRepository.loadUserByUsername("client@email.com").isEmpty()) {
            userService.insert(client);
        }
    }
}

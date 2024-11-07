package api_gestao_estacionamento.projeto.config;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.repository.UserRepository;
import api_gestao_estacionamento.projeto.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class DbSeedingConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void insertAdminIntoDb(){
        if(!userRepository.loadUserByUsername("admin@email.com").isPresent()){
            User user = new User();
            user.setUsername("admin@email.com");
            user.setPassword("123456");
            user.setRole(User.Role.ROLE_ADMIN);
            user.setCreatedBy("dbSeeding");
            user.setLastModifiedBy("dbSeeding");
            user.setCreatedAt(LocalDateTime.now());
            user.setLastModifiedAt(LocalDateTime.now());

            userService.insert(user);
        }
    }
}

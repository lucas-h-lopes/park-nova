package api_gestao_estacionamento.projeto.builder;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.util.ActivationTokenUtils;

import java.time.LocalDateTime;

public class UserBuilder {

    private User user;

    private UserBuilder(){}

    public static UserBuilder createInactiveUser(){
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.user = new User();
        userBuilder.user.setName("Lucas");
        userBuilder.user.setUsername("lucas@email.com");
        userBuilder.user.setRole(User.Role.ROLE_CLIENT);
        userBuilder.user.setActivationToken(ActivationTokenUtils.generateActivationToken());
        userBuilder.user.setCreatedAt(LocalDateTime.now());
        userBuilder.user.setCreatedBy("System");
        userBuilder.user.setLastModifiedAt(LocalDateTime.now());
        userBuilder.user.setLastModifiedBy("System");
        userBuilder.user.setPassword("123456");
        userBuilder.user.setActive(false);

        return userBuilder;
    }

    public static UserBuilder createOldInactiveUser(){
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.user = new User();
        userBuilder.user.setName("Lucas");
        userBuilder.user.setUsername("lucas@email.com");
        userBuilder.user.setRole(User.Role.ROLE_CLIENT);
        userBuilder.user.setActivationToken(ActivationTokenUtils.generateActivationToken());
        userBuilder.user.setCreatedAt(LocalDateTime.now().minusMonths(3));
        userBuilder.user.setCreatedBy("System");
        userBuilder.user.setLastModifiedAt(LocalDateTime.now().minusHours(25));
        userBuilder.user.setLastModifiedBy("System");
        userBuilder.user.setPassword("123456");
        userBuilder.user.setActive(false);

        return userBuilder;
    }

    public static UserBuilder createOldActiveUser(){
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.user = new User();
        userBuilder.user.setName("Lucas");
        userBuilder.user.setUsername("lucas@email.com");
        userBuilder.user.setRole(User.Role.ROLE_CLIENT);
        userBuilder.user.setActivationToken(ActivationTokenUtils.generateActivationToken());
        userBuilder.user.setCreatedAt(LocalDateTime.now().minusMonths(3));
        userBuilder.user.setCreatedBy("System");
        userBuilder.user.setLastModifiedAt(LocalDateTime.now().minusHours(25));
        userBuilder.user.setLastModifiedBy("System");
        userBuilder.user.setPassword("123456");
        userBuilder.user.setActive(true);

        return userBuilder;
    }

    public static UserBuilder createInvalidUser(){
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.user = new User();
        userBuilder.user.setName("Lucas");
        userBuilder.user.setUsername("lucas-email-com");
        userBuilder.user.setRole(User.Role.ROLE_CLIENT);
        userBuilder.user.setActivationToken(ActivationTokenUtils.generateActivationToken());
        userBuilder.user.setCreatedAt(LocalDateTime.now());
        userBuilder.user.setCreatedBy("System");
        userBuilder.user.setLastModifiedAt(LocalDateTime.now());
        userBuilder.user.setLastModifiedBy("System");
        userBuilder.user.setPassword("123456");
        userBuilder.user.setActive(false);

        return userBuilder;
    }

    public User get(){
        return this.user;
    }
}

package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.exception.InvalidActivationTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivationService {

    private final UserService userService;

    @Transactional
    public String activateAccount(Long id, String token) {
        User user = userService.findUserById(id);

        if (!user.getActivationToken().equals(token)) {
            throw new InvalidActivationTokenException("Token inválido");
        }

        userService.throwExceptionIfUserIsActive(user);

        if ((Duration.between(user.getLastModifiedAt(), LocalDateTime.now()).toHours() > 24) && (!user.isActive())) {
            return "Token expirado por inatividade, solicite a um administrador um novo código.";
        } else {
            user.setActive(true);
            return "Conta ativada com sucesso!";
        }
    }
}

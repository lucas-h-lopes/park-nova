package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.jwt.JwtUserDetails;
import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.repository.UserRepository;
import api_gestao_estacionamento.projeto.repository.projection.UserProjection;
import api_gestao_estacionamento.projeto.service.exception.EntityNotFoundException;
import api_gestao_estacionamento.projeto.service.exception.PasswordInvalidPassword;
import api_gestao_estacionamento.projeto.service.exception.UserIsAlreadyActiveException;
import api_gestao_estacionamento.projeto.service.exception.UsernameUniqueViolationException;
import api_gestao_estacionamento.projeto.util.ActivationTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public User insert(User u) {
        try {
            u.setPassword(encoder.encode(u.getPassword()));
            u.setName(u.getName().trim());
            u.setActivationToken(ActivationTokenUtils.generateActivationToken());
            return userRepository.save(u);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameUniqueViolationException(String.format("O nome de usuário '%s' já está cadastrado no sistema", u.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com id '%d' não foi encontrado no sistema", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<UserProjection> findAllUsers(Pageable pageable) {
        return userRepository.findAllPageable(pageable);
    }

    @Transactional
    public void updatePassword(String currentPassword, String newPassword, String confirmationPassword, JwtUserDetails details) {
        if (!newPassword.equals(confirmationPassword)) {
            throw new PasswordInvalidPassword("A nova senha e confirmação de senha não conferem");
        }
        User user = findUserById(details.getId());
        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordInvalidPassword("A senha atual não confere com a senha do usuário");
        }
        user.setPassword(encoder.encode(newPassword));
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public User loadUserByUsername(String username, boolean throwExceptionDefaultMessageIfNotFound) {
        if (throwExceptionDefaultMessageIfNotFound) {
            return userRepository.loadUserByUsername(username).orElseThrow(
                    () -> new EntityNotFoundException(String.format("Usuário com username '%s' não foi encontrado no sistema", username))
            );
        }
        return userRepository.loadUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Email e/ou senha inválidos")
        );
    }

    public void throwExceptionIfUserIsActive(User u) {
        if (u.isActive()) {
            throw new UserIsAlreadyActiveException("O usuário já está ativo no sistema");
        }
    }

    @Transactional(readOnly = true)
    public User.Role findRoleFromUsername(String username) {
        return userRepository.getRoleByUsername(username);
    }
}

package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.repository.UserRepository;
import api_gestao_estacionamento.projeto.repository.projection.UserProjection;
import api_gestao_estacionamento.projeto.service.exception.EntityNotFoundException;
import api_gestao_estacionamento.projeto.service.exception.UsernameUniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public User insert(User u) {
        try {
            u.setPassword(encoder.encode(u.getPassword()));
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
    public void deleteUserById(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User loadUserByUsername(String username) {
        return userRepository.loadUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com username '%s' não foi encontrado no sistema", username))
        );
    }

    public User.Role findRoleFromUsername(String username) {
        return userRepository.getRoleByUsername(username);
    }
}

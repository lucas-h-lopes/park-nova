package api_gestao_estacionamento.projeto.repository;

import api_gestao_estacionamento.projeto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

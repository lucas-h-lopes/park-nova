package api_gestao_estacionamento.projeto.repository;

import api_gestao_estacionamento.projeto.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}

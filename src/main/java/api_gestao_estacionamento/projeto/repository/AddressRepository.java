package api_gestao_estacionamento.projeto.repository;

import api_gestao_estacionamento.projeto.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

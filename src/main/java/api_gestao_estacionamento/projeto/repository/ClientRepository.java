package api_gestao_estacionamento.projeto.repository;

import api_gestao_estacionamento.projeto.model.Client;
import api_gestao_estacionamento.projeto.repository.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c.id AS id, c.cpf AS cpf, c.birthDate AS birthDate, NEW map(c.address.street AS street, c.address.number AS number, c.address.city AS city, c.address.state AS state, c.address.zipCode AS zipCode, c.address.country AS country) AS address from Client c")
    Page<ClientProjection> findAllPageable(Pageable pageable);

    @Query("select c from Client c where c.user.id = :id")
    Optional<Client> findByUserIdOptional(@Param("id") Long id);

    @Query("select c from Client c where c.id = :id")
    Client findByClientId(@Param("id") Long id);
}

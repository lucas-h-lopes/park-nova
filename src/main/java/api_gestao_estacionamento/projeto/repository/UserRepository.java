package api_gestao_estacionamento.projeto.repository;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u")
    Page<UserProjection> findAllPageable(Pageable pageable);
}

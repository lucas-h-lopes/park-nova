package api_gestao_estacionamento.projeto.repository;

import api_gestao_estacionamento.projeto.model.Spot;
import api_gestao_estacionamento.projeto.repository.projection.SpotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    @Query("select s from Spot s")
    Page<SpotProjection> findAllPageable(Pageable pageable);

    Optional<Spot> findByCode(String code);

    @Query("select s from Spot s where s.parkingSpotStatus = :status")
    Page<SpotProjection> findAllParameterized(Pageable pageable, @Param("status") Spot.ParkingSpotStatus status);
}

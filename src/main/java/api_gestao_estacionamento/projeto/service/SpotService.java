package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.model.Spot;
import api_gestao_estacionamento.projeto.repository.SpotRepository;
import api_gestao_estacionamento.projeto.repository.projection.SpotProjection;
import api_gestao_estacionamento.projeto.service.exception.CodeUniqueViolationException;
import api_gestao_estacionamento.projeto.service.exception.EntityNotFoundException;
import api_gestao_estacionamento.projeto.service.exception.SpotStatusInvalidException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SpotService {

    private SpotRepository spotRepository;

    public Spot insert(Spot spot) {
        try {
            return spotRepository.save(spot);
        } catch (DataIntegrityViolationException e) {
            throw new CodeUniqueViolationException(String.format("Vaga com código '%s' já está cadastrada no sistema", spot.getCode()));
        }
    }

    public Spot getByCode(String code) {
        return spotRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código '%s' não foi encontrada no sistema", code))
        );
    }

    public Page<SpotProjection> getAll(Pageable pageable) {
        return spotRepository.findAllPageable(pageable);
    }

    public Page<SpotProjection> getAllParameterized(Pageable pageable, String status) {
        try{
            Spot.ParkingSpotStatus.valueOf(status.toUpperCase());
        }catch(IllegalArgumentException | NoSuchElementException e){
            throw new SpotStatusInvalidException(String.format("O status informado '%s' não é um status válido", status));
        }
        return spotRepository.findAllParameterized(pageable, Spot.ParkingSpotStatus.valueOf(status.toUpperCase()));
    }

}

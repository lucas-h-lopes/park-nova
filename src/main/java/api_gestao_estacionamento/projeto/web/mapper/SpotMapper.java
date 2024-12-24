package api_gestao_estacionamento.projeto.web.mapper;

import api_gestao_estacionamento.projeto.model.Spot;
import api_gestao_estacionamento.projeto.service.exception.SpotStatusInvalidException;
import api_gestao_estacionamento.projeto.web.dto.spot.SpotCreateDto;
import api_gestao_estacionamento.projeto.web.dto.spot.SpotResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotMapper {

    public static Spot toSpot(SpotCreateDto dto){
        String oldStatus = dto.getStatus();

        dto.setStatus(dto.getStatus().toUpperCase());
        dto.setCode(dto.getCode().toUpperCase());

        try {
            return new Spot(dto);
        }catch(NoSuchElementException | IllegalArgumentException e){
            throw new SpotStatusInvalidException(String.format("O status informado '%s' não é um status válido", oldStatus));
        }
    }

    public static SpotResponseDto toResponseDto(Spot spot){
        return new SpotResponseDto(spot);
    }
}

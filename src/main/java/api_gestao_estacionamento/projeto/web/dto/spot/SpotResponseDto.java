package api_gestao_estacionamento.projeto.web.dto.spot;

import api_gestao_estacionamento.projeto.model.Spot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpotResponseDto {

    private Long id;
    private String code;
    private String status;

    public SpotResponseDto(Spot spot) {
        this.id = spot.getId();
        this.code = spot.getCode();
        this.status = spot.getParkingSpotStatus().name();
    }
}

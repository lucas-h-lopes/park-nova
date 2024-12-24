package api_gestao_estacionamento.projeto.repository.projection;

import api_gestao_estacionamento.projeto.model.Spot;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "code", "status"})
public interface SpotProjection {

    Long getId();

    String getCode();

    String getParkingSpotStatus();
}

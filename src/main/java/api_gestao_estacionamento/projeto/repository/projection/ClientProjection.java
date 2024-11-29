package api_gestao_estacionamento.projeto.repository.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonPropertyOrder({"id", "cpf", "birthDate", "address"})
public interface ClientProjection {

    Long getId();

    String getCpf();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate getBirthDate();

    AddressProjection getAddress();
}

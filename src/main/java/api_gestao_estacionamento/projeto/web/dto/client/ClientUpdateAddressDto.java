package api_gestao_estacionamento.projeto.web.dto.client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientUpdateAddressDto {
    @Schema(description = "Nome da rua do endereço do cliente.", example = "Rua das Flores")
    @NotBlank(message = "A rua não deve ser nula, vazia ou possuir somente espaços em branco")
    @Size(min = 6, max = 100, message = "A rua deve possuir de 6 a 100 caracteres")
    private String street;

    @Size(min = 1, max = 8, message = "O número deve possuir de 1 a 8 caracteres")
    @NotBlank(message = "O número não deve ser nulo, vazio ou possuir somente espaços em branco")
    @Schema(description = "Número do endereço do cliente.", example = "123")
    private String number;

    @Size(min = 4, max = 100, message = "A cidade deve possuir de 4 a 100 caracteres")
    @Schema(description = "Cidade do endereço do cliente.", example = "São Paulo")
    @NotBlank(message = "A cidade não deve ser nula, vazia ou possuir somente espaços em branco")
    private String city;

    @Size(min = 2, max = 2, message = "O estado deve possuir exatamente 2 caracteres")
    @Schema(description = "Estado do endereço do cliente, representado por sua sigla.", example = "SP")
    @NotBlank(message = "O estado não deve ser nulo, vazio ou possuir somente espaços em branco")
    private String state;

    @Size(min = 8, max = 8, message = "O CEP deve possuir exatamente 8 caracteres")
    @Schema(description = "CEP do cliente.", example = "12345678")
    @NotBlank(message = "O CEP não deve ser nulo, vazio ou possuir somente espaços em branco")
    private String zipCode;

    @Size(min = 2, max = 2, message = "O país deve possuir exatamente 2 caracteres")
    @Schema(description = "País do cliente, representado por sua sigla.", example = "BR")
    @NotBlank(message = "O País não deve ser nulo, vazio ou possuir somente espaços em branco")
    private String country;
}

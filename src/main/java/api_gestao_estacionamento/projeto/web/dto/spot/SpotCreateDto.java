package api_gestao_estacionamento.projeto.web.dto.spot;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SpotCreateDto {
    @NotBlank(message = "O código não pode ser nulo, vazio ou conter somente espaços em branco")
    @Size(min = 8, max = 8, message = "O código deve possuir exatamente 8  caracteres. p.ex.: AAA-0123")
    @Schema(description = "O código deve ser válido.", example = "AAA-0123")
    @Pattern(regexp = "[a-zA-Z]{3}-[0-9]{4}", message = "O código deve seguir o padrão: AAA-0123")
    private String code;
    @NotBlank(message = "O status não pode ser nulo, vazio ou conter somente espaços em branco")
    @Schema(description = "O status deve ser um status válido.", example = "AVAIABLE")
    private String status;
}

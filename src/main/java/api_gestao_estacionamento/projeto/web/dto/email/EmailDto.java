package api_gestao_estacionamento.projeto.web.dto.email;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class EmailDto {

    @NotBlank(message = "O username não pode ser nulo, vazio ou conter somente espaços em branco")
    @Pattern(regexp = "^[A-Za-z0-9.]{4,}@[A-Za-z0-9]{2,}\\.[A-Za-z]{2,}$", message = "Username precisa estar no seguinte formato: 'lucas@gmail.com'")
    @Schema(description = "O nome de usuário deve ser um e-mail válido.", example = "lucas@gmail.com")
    private String username;
    @NotNull(message = "Template não pode ser nulo, vazio ou conter somente espaços em branco")
    @Schema(description = "O template deve ser um template válido.", example = "welcome")
    private String template;
}

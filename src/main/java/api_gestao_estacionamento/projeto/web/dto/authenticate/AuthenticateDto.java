package api_gestao_estacionamento.projeto.web.dto.authenticate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateDto {
    @NotBlank(message = "Username não pode ser nulo, vazio ou conter somente espaços em branco")
    @Pattern(regexp = "^[A-Za-z0-9]{4,}@[A-Za-z0-9]{2,}\\.[A-Za-z]{2,}$", message = "Username precisa estar no seguinte formato: 'lucas@gmail.com'")
    @Schema(description = "O nome de usuário deve ser um e-mail válido.", example = "lucas@gmail.com")
    private String username;
    @NotBlank(message = "Password não pode ser nulo, vazio ou conter somente espaços em branco")
    @Size(min = 6, max = 15, message = "Password deve possuir de 6 a 15 caracteres")
    @Schema(description = "A senha do usuário, com entre 6 a 15 caracteres. Deve conter uma combinação de letras e números.", example = "senha123")
    private String password;
}

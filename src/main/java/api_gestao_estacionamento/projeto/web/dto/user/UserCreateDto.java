package api_gestao_estacionamento.projeto.web.dto.user;

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
public class UserCreateDto {
    @NotBlank(message = "O username não pode ser nulo, vazio ou conter somente espaços em branco")
    @Pattern(regexp = "^[A-Za-z0-9]{4,}@[A-Za-z0-9]{2,}\\.[A-Za-z]{2,}$", message = "Username precisa estar no seguinte formato: 'lucas@gmail.com'")
    private String username;
    @NotBlank(message = "A senha não pode ser nula, vazia ou conter somente espaços em branco")
    @Size(min = 6, max = 15, message = "Password deve possuir de 6 a 15 caracteres")
    private String password;
}

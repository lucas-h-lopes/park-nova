package api_gestao_estacionamento.projeto.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserNewPasswordDto {

    @NotBlank(message = "A senha atual não pode ser nula, vazia ou conter somente caracteres em branco")
    @Size(min = 6, max = 15, message = "A senha atual deve possuir de 6 a 15 caracteres")
    private String currentPassword;
    @NotBlank(message = "A nova senha não pode ser nula, vazia ou conter somente caracteres em branco")
    @Size(min = 6, max = 15, message = "A nova senha deve possuir de 6 a 15 caracteres")
    private String newPassword;
    @NotBlank(message = "A senha de confirmação não pode ser nula, vazia ou conter somente caracteres em branco")
    @Size(min = 6, max = 15, message = "A senha de confirmação deve possuir de 6 a 15 caracteres")
    private String confirmationPassword;
}

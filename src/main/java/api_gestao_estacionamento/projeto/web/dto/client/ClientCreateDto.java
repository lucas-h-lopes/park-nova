package api_gestao_estacionamento.projeto.web.dto.client;

import api_gestao_estacionamento.projeto.web.constraint.Data;
import api_gestao_estacionamento.projeto.web.constraint.MaxAge;
import api_gestao_estacionamento.projeto.web.constraint.MinAge;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateDto {

    @NotBlank(message = "A rua não deve ser nula, vazia ou possuir somente espaços em branco")
    private String street;
    @Size(min = 1, max = 8, message = "O número deve possuir de 1 a 8 caracteres")
    @NotBlank(message = "O número não deve ser nulo, vazio ou possuir somente espaços em branco")
    private String number;
    @Size(min = 4, max = 100, message = "A cidade deve possuir de 4 a 100 caracteres")
    @NotBlank(message = "A cidade não deve ser nula, vazia ou possuir somente espaços em branco")
    private String city;
    @Size(min = 2, max = 2, message = "O estado deve possuir exatamente 2 caracteres")
    @NotBlank(message = "O estado não deve ser nulo, vazio ou possuir somente espaços em branco")
    private String state;
    @Size(min = 8, max = 8, message = "O CEP deve possuir exatamente 8 caracteres")
    @NotBlank(message = "O CEP não deve ser nulo, vazio ou possuir somente espaços em branco")
    private String zipCode;
    @Size(min = 2, max = 2, message = "O país deve possuir exatamente 2 caracteres")
    @NotBlank(message = "O País não deve ser nulo, vazio ou possuir somente espaços em branco")
    private String country;
    @Size(min = 11, max = 11, message = "O CPF deve possuir exatamente 11 caracteres")
    @NotBlank(message = "O CPF não deve ser nulo, vazio ou possuir somente espaços em branco")
    @CPF
    private String cpf;
    @NotNull(message = "A data de nascimento não deve ser nula")
    @Past(message = "A data de nascimento deve ser uma data do passado")
    @MinAge(value = 18, message = "A data de nascimento deve ser maior ou igual a 18 anos")
    @MaxAge(value = 100, message = "A data de nascimento deve ser menor ou igual a 100 anos")
    @Data(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

}

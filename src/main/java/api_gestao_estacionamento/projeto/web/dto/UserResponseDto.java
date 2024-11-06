package api_gestao_estacionamento.projeto.web.dto;

import api_gestao_estacionamento.projeto.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;
    private String role;

    public UserResponseDto(User u){
        String role = u.getRole().name().substring("ROLE_".length());
        this.id = u.getId();
        this.username = u.getUsername();
        this.role = role;
    }
}
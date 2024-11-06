package api_gestao_estacionamento.projeto.web.mapper;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.web.dto.user.UserCreateDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User toUser(UserCreateDto dto) {
        return new User(dto);
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(user);
    }
}

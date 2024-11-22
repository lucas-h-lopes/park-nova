package api_gestao_estacionamento.projeto.web.mapper;

import api_gestao_estacionamento.projeto.model.Address;
import api_gestao_estacionamento.projeto.model.Client;
import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.web.dto.client.ClientCreateDto;
import api_gestao_estacionamento.projeto.web.dto.client.ClientResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDto dto) {
        Client client = new Client();
        client.setCpf(dto.getCpf());
        client.setBirthDate(dto.getBirthDate());
        client.setUser(new User());
        client.setAddress(Address.builder()
                .street(dto.getStreet())
                .number(dto.getNumber())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .country(dto.getCountry())
                .build());
        return client;
    }

    public static ClientResponseDto toDto(Client client) {
        LinkedHashMap<String, String> address = fillClientAddress(client.getAddress());

        ClientResponseDto responseDto = new ClientResponseDto();
        responseDto.setId(client.getId());
        responseDto.setCpf(client.getCpf());
        responseDto.setBirthDate(client.getBirthDate());
        responseDto.setAddress(address);

        return responseDto;
    }

    private static LinkedHashMap<String, String> fillClientAddress(Address address) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        result.put("street", address.getStreet());
        result.put("number", address.getNumber());
        result.put("zipCode", address.getZipCode());
        result.put("city", address.getCity());
        result.put("state", address.getState());
        result.put("country", address.getCountry());
        return result;
    }


}

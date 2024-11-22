package api_gestao_estacionamento.projeto.web.controller;

import api_gestao_estacionamento.projeto.jwt.JwtUserDetails;
import api_gestao_estacionamento.projeto.model.Client;
import api_gestao_estacionamento.projeto.service.ClientService;
import api_gestao_estacionamento.projeto.service.UserService;
import api_gestao_estacionamento.projeto.web.dto.client.ClientCreateDto;
import api_gestao_estacionamento.projeto.web.dto.client.ClientResponseDto;
import api_gestao_estacionamento.projeto.web.mapper.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> insert(@RequestBody @Valid ClientCreateDto dto, @AuthenticationPrincipal JwtUserDetails details) {
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.findUserById(details.getId()));
        clientService.insert(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{}")
                .buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(ClientMapper.toDto(client));
    }
}

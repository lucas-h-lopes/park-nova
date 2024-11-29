package api_gestao_estacionamento.projeto.web.controller;

import api_gestao_estacionamento.projeto.jwt.JwtUserDetails;
import api_gestao_estacionamento.projeto.model.Client;
import api_gestao_estacionamento.projeto.repository.projection.ClientProjection;
import api_gestao_estacionamento.projeto.service.ClientService;
import api_gestao_estacionamento.projeto.service.UserService;
import api_gestao_estacionamento.projeto.web.dto.client.ClientCreateDto;
import api_gestao_estacionamento.projeto.web.dto.client.ClientResponseDto;
import api_gestao_estacionamento.projeto.web.dto.client.ClientUpdateAddressDto;
import api_gestao_estacionamento.projeto.web.dto.pageable.PageableDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserResponseDto;
import api_gestao_estacionamento.projeto.web.exception.CustomExceptionBody;
import api_gestao_estacionamento.projeto.web.mapper.ClientMapper;
import api_gestao_estacionamento.projeto.web.mapper.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @Operation(summary = "Criar cliente", description = "Realiza o cadastro de cliente para um usuário autenticado no sistema.", tags = {"Clientes", "Criar"}, security = @SecurityRequirement(name = "security"), responses = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso!", headers = @Header(name = HttpHeaders.LOCATION, description = "URL do novo cliente"), content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado no sistema.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "422", description = "Dados informados são inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "400", description = "Formatação da data é inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> insert(@RequestBody @Valid ClientCreateDto dto, @AuthenticationPrincipal JwtUserDetails details) {
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.findUserById(details.getId()));
        clientService.insert(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(ClientMapper.toDto(client));
    }

    @Operation(summary = "Buscar clientes", description = "Retorna todos os clientes cadastrados no sistema. Recurso possui paginação e é necessário estar autenticado como administrador para acessá-lo.", security = @SecurityRequirement(name = "security"), tags = {"Clientes", "Buscar todos"}, parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "page", description = "Número da página a ser retornada na consulta.", content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
            @Parameter(in = ParameterIn.QUERY, name = "size", description = "Quantidade de elementos exibidos por página.", content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))),
            @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true, description = "Critério de ordenação dos elementos retornados.", array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")))
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Clientes retornados com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDto> getAll(@PageableDefault(size = 5) @Parameter(hidden = true) Pageable pageable) {
        Page<ClientProjection> result = clientService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(result));
    }

    @Operation(summary = "Visualizar detalhes do cliente", description = "Acessa as informações do registro de cliente associado ao usuário autenticado no sistema.", tags = {"Clientes", "Buscar um"}, security = @SecurityRequirement(name = "security"), responses = {
            @ApiResponse(responseCode = "200", description = "Retornado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "O usuário autenticado não possui cadastro de cliente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @GetMapping("/client-details")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> getDetails(@AuthenticationPrincipal JwtUserDetails details) {
        Client client = clientService.getByUserId(details.getId());
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }

    @Operation(summary = "Excluir cliente", description = "Exclui um usuário existente pelo seu Id. É necessário estar autenticado para acessar este recurso.", security = @SecurityRequirement(name = "security"), parameters = @Parameter(in = ParameterIn.PATH, name = "id", description = "Id de um cliente cadastrado no sistema."), tags = {"Clientes", "Excluir"},responses = {
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Cliente e/ou endereço não encontrado(s).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @AuthenticationPrincipal JwtUserDetails details) {
        clientService.deleteById(id, details);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar cliente", description = "Retorna um cliente existente pelo seu Id. É necessário estar autenticado como administrador para acessar este recurso.", security = @SecurityRequirement(name = "security"), tags = {"Clientes", "Buscar um"}, responses = {
            @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    }, parameters = @Parameter(in = ParameterIn.PATH, name = "id", description = "Id de um usuário cadastrado no sistema."))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getById(@PathVariable Long id) {
        Client client = clientService.getById(id);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }
    @Operation(summary = "Atualizar cliente", description = "Atualiza o endereço do cliente com base no usuário autenticado que enviou a requisição. É necessário estar autenticado como CLIENTE para acessar este recurso.", security = @SecurityRequirement(name = "security"), tags = {"Clientes", "Atualizar"}, responses = {
            @ApiResponse(responseCode = "204", description = "Endereço atualizado com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Dados informados são inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "O usuário não possui registro de cliente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping
    public ResponseEntity<Void> updateAddress(@AuthenticationPrincipal JwtUserDetails details, @RequestBody @Valid ClientUpdateAddressDto dto) {
        clientService.updateAddress(dto.getStreet(), dto.getNumber(), dto.getZipCode(), dto.getCity(), dto.getState(), dto.getCountry(), details);
        return ResponseEntity.noContent().build();
    }

}

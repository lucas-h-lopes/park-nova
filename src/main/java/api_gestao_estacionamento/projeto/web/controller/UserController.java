package api_gestao_estacionamento.projeto.web.controller;

import api_gestao_estacionamento.projeto.jwt.JwtUserDetails;
import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.repository.projection.UserProjection;
import api_gestao_estacionamento.projeto.service.UserService;
import api_gestao_estacionamento.projeto.web.dto.pageable.PageableDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserCreateDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserNewPasswordDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserResponseDto;
import api_gestao_estacionamento.projeto.web.exception.CustomExceptionBody;
import api_gestao_estacionamento.projeto.web.mapper.PageableMapper;
import api_gestao_estacionamento.projeto.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Criar usuário", description = "Insere um usuário no sistema.", tags = {"Usuarios","Criar"}, responses = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso!", headers = @Header(name = HttpHeaders.LOCATION, description = "URL do novo usuário"), content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Username já cadastrado no sistema.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "422", description = "Dados informados são inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> insert(@RequestBody @Valid UserCreateDto dto) {
        User user = UserMapper.toUser(dto);
        userService.insert(user);
        UserResponseDto result = UserMapper.toDto(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @Operation(summary = "Buscar usuário", description = "Retorna um usuário existente pelo seu Id. É necessário estar autenticado para acessar este recurso.", security = @SecurityRequirement(name = "security"), tags = {"Usuarios","Buscar um"}, responses = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    }, parameters = @Parameter(in = ParameterIn.PATH, name = "id", description = "Id de um usuário cadastrado no sistema."))
    @PreAuthorize("hasRole('ADMIN') || (hasRole('CLIENT') && #id == authentication.principal.id)")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Buscar usuários", description = "Retorna todos os usuários cadastrados no sistema. Recurso possui paginação e é necessário estar autenticado para acessá-lo.", security = @SecurityRequirement(name = "security"), tags = {"Usuarios","Buscar todos"}, parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "page", description = "Número da página a ser retornada na consulta.", content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
            @Parameter(in = ParameterIn.QUERY, name = "size", description = "Quantidade de elementos exibidos por página.", content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))),
            @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true, description = "Critério de ordenação dos elementos retornados.", array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")))
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true) @PageableDefault(size = 5) Pageable pageable) {
        Page<UserProjection> projection = userService.findAllUsers(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(projection));
    }

    @Operation(summary = "Excluir usuário", description = "Exclui um usuário existente pelo seu Id. É necessário estar autenticado para acessar este recurso.", security = @SecurityRequirement(name = "security"), tags = {"Usuarios","Excluir"}, parameters = @Parameter(in = ParameterIn.PATH, name = "id", description = "Id de um usuário cadastrado no sistema."), responses = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PreAuthorize("hasRole('ADMIN') || (hasRole('CLIENT') && #id == authentication.principal.id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar usuário",description = "Atualiza a senha do usuário autenticado que enviou a requisição. É necessário estar autenticado para acessar este recurso.",security = @SecurityRequirement(name = "security"), tags = {"Usuarios","Atualizar"}, responses = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "O usuário não possui permissão para visualizar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "422", description = "Dados informados são inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "400", description = "Possíveis causas:<br/>" +
                    "- A nova senha e confirmação de senha não conferem<br/>" +
                    "- A senha atual não confere com a senha do usuário", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    @PutMapping
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserNewPasswordDto dto, @AuthenticationPrincipal JwtUserDetails details) {
        userService.updatePassword(dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmationPassword(), details);
        return ResponseEntity.noContent().build();
    }
}

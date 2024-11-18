package api_gestao_estacionamento.projeto.web.controller;

import api_gestao_estacionamento.projeto.jwt.JwtToken;
import api_gestao_estacionamento.projeto.jwt.JwtUserDetailsService;
import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.UserService;
import api_gestao_estacionamento.projeto.service.exception.InactiveAccountException;
import api_gestao_estacionamento.projeto.web.dto.authenticate.AuthenticateDto;
import api_gestao_estacionamento.projeto.web.exception.CustomExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/login")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Autenticar usuário", description = "Autentica o usuário com as credenciais fornecidas e retorna um token JWT.",
            tags = {"Login"}, responses = {
            @ApiResponse(responseCode = "200", description = "Autenticado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtToken.class))),
            @ApiResponse(responseCode = "400", description = "Credenciais informadas são inválidas.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Usuário está inativo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class))),
            @ApiResponse(responseCode = "422", description = "Dados informados são inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticateDto dto, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken tempToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(tempToken);

            User user = userService.loadUserByUsername(dto.getUsername(), false);

            if (!user.isActive()) {
                throw new InactiveAccountException(String.format("Usuário '%s' inativo. Autenticação não permitida", dto.getUsername()));
            }

            return ResponseEntity.ok(userDetailsService.generateToken(dto.getUsername()));
        } catch (AuthenticationException e) {
            log.info("Credenciais inválidas para o usuário {}", dto.getUsername());
        }
        return ResponseEntity.badRequest().body(
                new CustomExceptionBody(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}

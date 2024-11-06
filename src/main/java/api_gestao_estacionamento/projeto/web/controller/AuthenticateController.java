package api_gestao_estacionamento.projeto.web.controller;

import api_gestao_estacionamento.projeto.jwt.JwtToken;
import api_gestao_estacionamento.projeto.jwt.JwtUserDetailsService;
import api_gestao_estacionamento.projeto.web.dto.authenticate.AuthenticateDto;
import api_gestao_estacionamento.projeto.web.exception.CustomExceptionBody;
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
    private JwtUserDetailsService service;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticateDto dto, HttpServletRequest request){
        try{
            UsernamePasswordAuthenticationToken tempToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(tempToken);

           return ResponseEntity.ok(service.generateToken(dto.getUsername()));
        }catch(AuthenticationException e){
            log.info("Credenciais inválidas para o usuário {}", dto.getUsername());
        }
        return ResponseEntity.badRequest().body(
                new CustomExceptionBody(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}

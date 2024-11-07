package api_gestao_estacionamento.projeto.jwt;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Authentication", "bearer realm='/api/v1/login'");
        response.setContentType("application/json");
        response.setStatus(401);
        if(request.getHeader("Authorization") == null) {
            response.getWriter().write("{\"error\":\"Você precisa estar autenticado para acessar este recurso\"}");
        }else{
            response.getWriter().write("{\"error\":\"O token informado é inválido ou está expirado\"}");
        }
    }
}

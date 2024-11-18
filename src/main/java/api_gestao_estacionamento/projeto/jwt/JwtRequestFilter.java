package api_gestao_estacionamento.projeto.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String path = request.getRequestURI();
        log.info("PATH {}", path);
        if (!path.startsWith("/api/v1/users/activate-account/") &&
                (!path.equals("/api/v1/users") || !request.getMethod().equals("POST")) &&
                !path.equals("/api/v1/login")) {
            if (token == null || !token.startsWith("Bearer ")) {
                log.info("Token está nulo ou não inicializado com 'Bearer '");
                filterChain.doFilter(request, response);
                return;
            }
            if (!jwtUtils.isTokenValid(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            String username = jwtUtils.getSubject(token);
            authenticate(username, request);
        }
        filterChain.doFilter(request, response);
    }

    public void authenticate(String username, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token =
                UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}

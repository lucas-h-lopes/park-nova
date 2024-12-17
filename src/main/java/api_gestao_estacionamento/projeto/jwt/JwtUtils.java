package api_gestao_estacionamento.projeto.jwt;

import api_gestao_estacionamento.projeto.config.properties.JwtConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfigProperties jwtConfigProperties;

    private SecretKey generateSecretKey(){
        return Keys.hmacShaKeyFor(jwtConfigProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    private final Integer EXPIRATION_MINUTES = 30;

    private Date calculateExpiration(Date start){
        LocalDateTime localDateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusMinutes(EXPIRATION_MINUTES);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public JwtToken generateToken(String username, String role){
        Date start = new Date();
        Date expiration = calculateExpiration(start);
        try{
            String token = Jwts.builder().header().add("typ", "JWT").and()
                    .expiration(expiration)
                    .issuedAt(start)
                    .subject(username)
                    .claim("role", role)
                    .signWith(generateSecretKey())
                    .compact();
            return new JwtToken(token);
        }catch(JwtException e){
            log.info("Falha na geração do token: ", e);
        }
        return null;
    }

    private Claims getClaimsFromToken(String token){
        try{
            return Jwts.parser().verifyWith(generateSecretKey()).build()
                    .parseSignedClaims(removeBearerFromToken(token)).getPayload();
        }catch(JwtException e){
            log.info("Falha ao retornar os dados do token: ", e);
        }
        return null;
    }

    public String getSubject(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parser().verifyWith(generateSecretKey()).build()
                    .parseSignedClaims(removeBearerFromToken(token));
            return true;
        }catch(JwtException e){
            log.info("Token inválido ou expirado: {}", e);
        }
        return false;
    }

    private String removeBearerFromToken(String token){
        if(token.startsWith(jwtConfigProperties.getBearer())){
            return token.substring(jwtConfigProperties.getBearer().length());
        }
        return token;
    }
}

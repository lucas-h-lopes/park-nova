package api_gestao_estacionamento.projeto.util;

import api_gestao_estacionamento.projeto.jwt.JwtToken;
import api_gestao_estacionamento.projeto.web.dto.user.UserCreateDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Component
public class TestUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Consumer<HttpHeaders> login(WebTestClient client, String username, String password){
        UserCreateDto dto = UserCreateDto.builder()
                .username(username)
                .password(password)
                .build();
        String token = client.post().uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class).returnResult().getResponseBody()
                .getToken();

        return x -> x.add(HttpHeaders.AUTHORIZATION, "Bearer ".concat(token));
    }

    public void clearDatabase(String table) {
        String sql = "TRUNCATE TABLE " + table + " RESTART IDENTITY;";
        jdbcTemplate.execute(sql);
    }
}

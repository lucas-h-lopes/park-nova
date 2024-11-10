package api_gestao_estacionamento.projeto;

import api_gestao_estacionamento.projeto.config.TestConfig;
import api_gestao_estacionamento.projeto.web.dto.user.UserCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProjetoApplication.class)
@Sql(scripts = "classpath:sql/user/user-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/user/user-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Import(TestConfig.class)
public class UserIT {

    @Autowired
    private WebTestClient testClient;
    private final String baseURI = "/api/v1/users";

    @Test
    public void criarUsuario_comDadosValidos_retornar201EUserResponseDto() {
        UserCreateDto dto = UserCreateDto.builder()
                .username("testeuser@email.com")
                .password("123456")
                .build();

        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("id").isEqualTo(1)
                .jsonPath("username").isEqualTo("testeuser@email.com")
                .jsonPath("role").isEqualTo("CLIENT");
    }

    @Test
    public void criarUsuario_comDadosInvalidos_retornar422ECustomErrorMessage(){
        UserCreateDto dto = UserCreateDto.builder()
                .username("testeuser@email.com")
                .password("123456")
                .build();

        dto.setUsername("");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setUsername(" ");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setUsername("lucas-teste@gmail.com");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setUsername("ab@email.com");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setUsername("abc@email.com");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setUsername("abc1@e.com");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setUsername("abc1@ee.c");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setUsername("testevalido@email.com");
        dto.setPassword("");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setPassword(" ");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setPassword("12345");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");

        dto.setPassword("1234567890123456");
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody().jsonPath("path").isEqualTo(baseURI)
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void criarUsuario_comUsuariosJaExistente_retornar409ECustomErrorMessage(){
        UserCreateDto dto = UserCreateDto.builder()
                .username("client@gmail.com")
                .password("123456")
                .build();
        testClient.post()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("POST");
    }
}

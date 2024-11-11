package api_gestao_estacionamento.projeto;

import api_gestao_estacionamento.projeto.config.TestConfig;
import api_gestao_estacionamento.projeto.util.TestUtils;
import api_gestao_estacionamento.projeto.web.dto.pageable.PageableDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserCreateDto;
import api_gestao_estacionamento.projeto.web.dto.user.UserNewPasswordDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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
@Slf4j
public class UserIT {

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private WebTestClient testClient;
    private final String baseURI = "/api/v1/users";

    @Test
    public void criarUsuario_comDadosValidos_retornar201EUserResponseDto() {
        testUtils.clearDatabase("users");
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
    public void criarUsuario_comDadosInvalidos_retornar422ECustomErrorMessage() {
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
    public void criarUsuario_comUsuariosJaExistente_retornar409ECustomErrorMessage() {
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

    @Test
    public void buscarUsuario_comIdExistenteUsuarioClient_retornarStatus200EUserResponseDto() {
        testClient.get()
                .uri(baseURI + "/101")
                .headers(testUtils.login(testClient, "client@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(101)
                .jsonPath("username").isEqualTo("client@gmail.com")
                .jsonPath("role").isEqualTo("CLIENT");
    }

    @Test
    public void buscarUsuario_comIdExistenteUsuarioAdmin_retornarStatus200EUserResponseDto() {
        testClient.get()
                .uri(baseURI + "/100")
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(100)
                .jsonPath("username").isEqualTo("admin@gmail.com")
                .jsonPath("role").isEqualTo("ADMIN");

        testClient.get()
                .uri(baseURI + "/101")
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(101)
                .jsonPath("username").isEqualTo("client@gmail.com")
                .jsonPath("role").isEqualTo("CLIENT");
    }

    @Test
    public void buscarUsuario_comIdInexistenteUsuarioAdmin_retornarStatus200EUserResponseDto() {
        testClient.get()
                .uri(baseURI + "/0")
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo(baseURI.concat("/0"));
    }

    @Test
    public void buscarUsuario_comIdOutroUsuarioComUsuarioClient_retornarStatus403ECustomErrorMessage() {
        testClient.get()
                .uri(baseURI + "/100")
                .headers(testUtils.login(testClient, "client@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("path").isEqualTo(baseURI + "/100")
                .jsonPath("method").isEqualTo("GET");
    }

    @Test
    public void buscarUsuario_usuarioNaoAutenticado_retornarStatus401(){
        testClient.get()
                .uri(baseURI + "/100")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("error").isEqualTo("Você precisa estar autenticado para acessar este recurso");

        testClient.get()
                .uri(baseURI + "/100")
                .header("Authorization", "token invalido")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("error").isEqualTo("O token informado é inválido ou está expirado");
    }

    @Test
    public void buscarTodos_comUsuarioClient_retornarStatus403ECustomErrorMessage() {
        testClient.get()
                .uri(baseURI)
                .headers(testUtils.login(testClient, "client@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo(baseURI);
    }

    @Test
    public void buscarTodos_comUsuarioAdmin_retornarStatus200EPageableDto() {
        PageableDto result = testClient.get()
                .uri(baseURI)
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(result.getNumber()).isEqualTo(0);
        Assertions.assertThat(result.getNumberOfElements()).isEqualTo(2);
        Assertions.assertThat(result.getSize()).isEqualTo(5);

        result = testClient.get()
                .uri(baseURI + "?size=1")
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(result.getNumber()).isEqualTo(0);
        Assertions.assertThat(result.getNumberOfElements()).isEqualTo(1);
        Assertions.assertThat(result.getSize()).isEqualTo(1);

        result = testClient.get()
                .uri(baseURI + "?size=1&page=1")
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(result.getNumber()).isEqualTo(1);
        Assertions.assertThat(result.getNumberOfElements()).isEqualTo(1);
        Assertions.assertThat(result.getSize()).isEqualTo(1);
    }

    @Test
    public void buscarTodos_usuarioNaoAutenticado_retornarStatus401(){
        testClient.get()
                .uri(baseURI)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("error").isEqualTo("Você precisa estar autenticado para acessar este recurso");

        testClient.get()
                .uri(baseURI)
                .header("Authorization", "token invalido")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("error").isEqualTo("O token informado é inválido ou está expirado");
    }

    @Test
    public void excluirUsuario_comIdDiferenteUsuarioClient_retornarStatus403ECustomErrorMessage() {
        testClient.delete()
                .uri(baseURI + "/100")
                .headers(testUtils.login(testClient, "client@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("DELETE")
                .jsonPath("path").isEqualTo(baseURI + "/100");
    }

    @Test
    public void excluirUsuario_comIdIgualUsuarioClient_retornarStatus204() {
        testClient.delete()
                .uri(baseURI + "/101")
                .headers(testUtils.login(testClient, "client@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void excluirUsuario_usuarioAdmin_retornarStatus204NoContent() {
        testClient.delete()
                .uri(baseURI + "/101")
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();

        testClient.delete()
                .uri(baseURI + "/100")
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void excluirUsuario_IdInexistentComUsuarioAdmin_retornarStatus404ECustomErrorMessage() {
        testClient.delete()
                .uri(baseURI + "/0")
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("path").isEqualTo(baseURI + "/0")
                .jsonPath("method").isEqualTo("DELETE");
    }

    @Test
    public void excluirUsuario_usuarioNaoAutenticado_retornarStatus401(){
        testClient.delete()
                .uri(baseURI + "/1")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("error").isEqualTo("Você precisa estar autenticado para acessar este recurso");

        testClient.delete()
                .uri(baseURI + "/1")
                .header("Authorization", "token invalido")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("error").isEqualTo("O token informado é inválido ou está expirado");
    }

    @Test
    public void alterarSenha_comDadosValidos_retornarStatus204() {
        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "123455", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "123455", "123455"))
                .headers(testUtils.login(testClient, "client@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void alterarSenha_comDadosInvalidos_retornarStatus422ECustomErrorMessage() {
        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("", "123455", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto(" ", "123455", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("12345", "123455", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("1234567890123456", "123455", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", " ", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "12345", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "1234567890123456", "123455"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "123457", ""))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "123457", " "))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "123457", "12345"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

        testClient.put()
                .uri(baseURI)
                .bodyValue(new UserNewPasswordDto("123456", "123457", "1234567890123456"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo(baseURI)
                .jsonPath("method").isEqualTo("PUT");

    }

    @Test
    public void alterarSenha_comDadosInvalidos_retornarStatus400ECustomErrorMessage(){
        testClient.put()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserNewPasswordDto("123457", "123456", "123456"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("PUT")
                .jsonPath("path").isEqualTo(baseURI);

        testClient.put()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserNewPasswordDto("123456", "123457", "123456"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("PUT")
                .jsonPath("path").isEqualTo(baseURI);

        testClient.put()
                .uri(baseURI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserNewPasswordDto("123456", "123456", "123457"))
                .headers(testUtils.login(testClient, "admin@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("PUT")
                .jsonPath("path").isEqualTo(baseURI);
    }

    @Test
    public void alterarSenha_usuarioNaoAutenticado_retornarStatus401(){
        testClient.put()
                .uri(baseURI)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("error").isEqualTo("Você precisa estar autenticado para acessar este recurso");

        testClient.put()
                .uri(baseURI)
                .header("Authorization", "token invalido")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("error").isEqualTo("O token informado é inválido ou está expirado");
    }
}

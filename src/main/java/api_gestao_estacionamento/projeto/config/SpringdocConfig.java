package api_gestao_estacionamento.projeto.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components().addSecuritySchemes("security", scheme()))
                .info(new Info()
                        .title("Park Nova API")
                        .description("API dedicada ao gerenciamento eficiente de estacionamentos, possibilitando controle de usuários, clientes, vagas, check-in e check-out")
                        .contact(new Contact().name("Lucas Henrique Lopes - LinkedIn").url("https://www.linkedin.com/in/lucashlopes"))
                        .version("v1"))
                .tags(Arrays.asList(
                                new Tag().name("Login").description("Recurso para autenticar no sistema"),
                                new Tag().name("Usuarios").description("Recursos para gerenciamento de usuários"),
                                new Tag().name("Clientes").description("Recursos para gerenciamento de clientes"),
                                new Tag().name("Criar").description("Endpoints para criar um recurso"),
                                new Tag().name("Buscar um").description("Endpoints para buscar um único recurso"),
                                new Tag().name("Buscar todos").description("Endpoints para buscar todos os recurso de forma paginada"),
                                new Tag().name("Atualizar").description("Endpoints para atualizar um recurso"),
                                new Tag().name("Excluir").description("Endpoints para excluir um recurso"),
                                new Tag().name("E-mail").description("Endpoints responsáveis pelo envio de e-mails ou que realizam operações relacionadas")
                        )
                );
    }

    @Bean
    public SecurityScheme scheme() {
        return new SecurityScheme().name("security")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .description("Realize a autenticação informando um Bearer token válido")
                .scheme("bearer");
    }
}

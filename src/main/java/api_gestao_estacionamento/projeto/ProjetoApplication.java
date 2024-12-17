package api_gestao_estacionamento.projeto;

import api_gestao_estacionamento.projeto.config.properties.BaseUrlConfigProperties;
import api_gestao_estacionamento.projeto.config.properties.JwtConfigProperties;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProjetoApplication {

    public static void main(String[] args) {
		//SpringApplication.run(ProjetoApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ProjetoApplication.class);

		builder.bannerMode(Banner.Mode.OFF);
		builder.properties("server.port=8081");

		builder.run(args);

		/* debug
		=========
		ConfigurableApplicationContext context = builder.context();
		BaseUrlConfigProperties baseUrlConfigProperties = context.getBean(BaseUrlConfigProperties.class);
		JwtConfigProperties jwtConfigProperties = context.getBean(JwtConfigProperties.class);

		System.out.println("Login url: " + baseUrlConfigProperties.getLogin());
		System.out.println("User url: " + baseUrlConfigProperties.getUser());

		System.out.println("Bearer: " + jwtConfigProperties.getBearer());
		System.out.println("Authorization: " + jwtConfigProperties.getAuthorization());
		System.out.println("Secret: " + jwtConfigProperties.getSecret());
		 */
	}

}

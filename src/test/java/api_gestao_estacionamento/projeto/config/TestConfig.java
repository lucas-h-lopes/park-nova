package api_gestao_estacionamento.projeto.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean(name = "dbSeedingConfig")
    public DbSeedingConfig disableDbSeeding(){
        return null;
    }
}

package api_gestao_estacionamento.projeto.config;

import api_gestao_estacionamento.projeto.jwt.JwtEntryPoint;
import api_gestao_estacionamento.projeto.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebMvc
public class SecurityConfig {

    @Value("${user.baseUrl}")
    private String userResourceBaseUrl;

    @Value("${login.authenticate}")
    private String authenticateResourceBaseUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.csrf(x -> x.disable())
                .formLogin(x -> x.disable())
                .httpBasic(x -> x.disable())
                .authorizeHttpRequests(x -> x.requestMatchers(
                        antMatcher(HttpMethod.POST, userResourceBaseUrl),
                        antMatcher(HttpMethod.POST, authenticateResourceBaseUrl),
                        antMatcher("/documentacao/**"),
                        antMatcher("/swagger-ui"),
                        antMatcher("/swagger-ui/**"),
                        antMatcher("/v3/api-docs/**")
                ).permitAll().anyRequest().authenticated())
                .exceptionHandling(x -> x.authenticationEntryPoint(new JwtEntryPoint()))
                .addFilterBefore(requestFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public JwtRequestFilter requestFilter(){
        return new JwtRequestFilter();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

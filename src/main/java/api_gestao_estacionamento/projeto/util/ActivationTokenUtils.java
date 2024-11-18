package api_gestao_estacionamento.projeto.util;

import api_gestao_estacionamento.projeto.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ActivationTokenUtils {

    public static String generateActivationToken() {
        String token = BCrypt.gensalt();
        if (token.charAt(token.length()-1) == '.') {
            token = token.replace(".", "");
        }
        return token;
    }
}

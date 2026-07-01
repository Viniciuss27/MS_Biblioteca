package vinix.config;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class AppConfig {

    @Value("${jwt.secret}")
    private String secret;


    // criptografa (hash) senhas dos usuários de forma segura
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //assina e verifica tokens JWT
    @Bean
    SecretKey jwtSecretKey() {
        // Converte a string "secret" para bytes e depois codifica em Base64,
        byte[] keyBytes = Decoders.BASE64.decode(
                java.util.Base64.getEncoder().encodeToString(secret.getBytes())
        );

        // Gera a chave HMAC para ser usada na assinatura dos JWTs
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
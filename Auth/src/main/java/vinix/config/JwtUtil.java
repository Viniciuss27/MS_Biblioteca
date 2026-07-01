package vinix.config;

import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import vinix.entities.Funcionario;

@Component
public class JwtUtil {

    @Autowired
    private SecretKey jwtSecretKey;

    //Tempo de expiração do token no yml
    @Value("${jwt.expiration}")
    private Long expiration;

    // Gera um token JWT com base nos dados do funcionário
    public String generateToken(Funcionario funcionario) {
        return Jwts.builder()
            // Define o "subject" do token(Usuario)
            .subject(funcionario.getEmail())

            //claim customizada, "roles" contendo a lista de nomes
            .claim("roles", funcionario.getRoles().stream()
                    .map(r -> r.getRoleName())
                    .collect(Collectors.toList()))

            //data/hora em que o token foi emitido
            .issuedAt(new Date())

            //data/hora de expiração do token,
            .expiration(new Date(System.currentTimeMillis() + expiration))

            // Assina o token com a chave secreta
            .signWith(jwtSecretKey)

            //compacta a string final do token(JWT)
            .compact();
    }
}
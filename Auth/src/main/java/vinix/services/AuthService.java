package vinix.services;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vinix.config.JwtUtil;
import vinix.entities.Funcionario;
import vinix.repositories.FuncionarioRepository;

@Service
public class AuthService implements UserDetailsService {

    @Value("${security.oauth2.client.name}")
    private String name;

    @Value("${security.oauth2.client.secret}")
    private String senha;

    @Autowired
    private FuncionarioRepository rep;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //compara a senha do funcionário (texto puro) com o hash salvo no banco

    @Autowired
    private JwtUtil jwtUtil;
    // Gera o token JWT após autenticação bem-sucedida

    
    //Autentica "client" e o funcionário, retorna um token JWT se tudo estiver correto
    public String authenticate(String clientId, String clientSecret, String email, String password) {

        // Valida se o client (clientId/clientSecret) é o esperado.
        if (!name.equals(clientId) || !constantTimeEquals(senha, clientSecret)) {
            throw new RuntimeException("Client inválido");
        }

        // Busca o funcionário pelo email
        Funcionario funcionario = rep.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado: " + email));

        // Compara a senha informada com o hash salvo no banco
        if (!passwordEncoder.matches(password, funcionario.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        //gera e retorna o token JWT
        return jwtUtil.generateToken(funcionario);
    }

    
     // Compara duas strings em tempo constante, evita vazar informação (timing attack)
    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        return MessageDigest.isEqual(
            a.getBytes(StandardCharsets.UTF_8),
            b.getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return rep.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado: " + username));
    }
}
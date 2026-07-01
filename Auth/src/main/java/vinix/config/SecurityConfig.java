package vinix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    // bean de criptografia usado para comparar a senha digitada 
    
    @Autowired
    private UserDetailsService userDetailsService;
    // serviço que busca o usuário no banco pelo email
  
    @Bean // Autenticador: recebe email+senha → busca usuário → compara senha → autentica
    DaoAuthenticationProvider authenticationProvider() {
        // componente que faz a autenticação buscando o usuário no banco
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        // busca o usuário (pelo UserDetailsService)
        provider.setPasswordEncoder(passwordEncoder);
        // compara a senha (BCrypt)
        return provider;
    }

    @Bean // É o orquestrador da autenticação
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
        // vai injetar e usar o DaoAuthenticationProvider para autenticar o usuário
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // desativa proteção CSRF pois a API é stateless (usa JWT, não sessão)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // STATELESS = o servidor não guarda sessão do usuário
            // cada requisição precisa trazer o token JWT para se identificar

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/token").permitAll()
                // libera o login sem token, para o usuario enviar email+senha e receber o JWT
                .anyRequest().authenticated()
                // todos os outros endpoints exigem token JWT válido
            );

        return http.build();
    }
}
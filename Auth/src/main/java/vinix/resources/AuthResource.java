package vinix.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vinix.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private AuthService authService;

	// retornando um token JWT em caso de sucesso.
	@PostMapping(value = "/token")
	public ResponseEntity<String> login(
			@RequestHeader("client-id") String clientId,
			@RequestHeader("client-secret") String clientSecret, 
			@RequestParam String email,
			@RequestParam String password) {

		// Delega toda a lógica de validação e geração do token para o AuthService
		return ResponseEntity.ok(authService.authenticate(clientId, clientSecret, email, password));
	}
}
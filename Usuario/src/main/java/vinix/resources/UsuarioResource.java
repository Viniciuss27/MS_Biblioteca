package vinix.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import vinix.entities.Usuario;
import vinix.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	@Autowired
	private UsuarioService serv;

	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		return ResponseEntity.ok(serv.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Long id) {
		return ResponseEntity.ok(serv.findById(id));
	}

	@GetMapping(value = "/cpf/{cpf}")
	public ResponseEntity<List<Usuario>> findByCpf(@PathVariable String cpf) {
		return ResponseEntity.ok(serv.findByCpf(cpf));
	}

	@PostMapping
	public ResponseEntity<Usuario> insert(@RequestBody Usuario usuario) {
		usuario = serv.insert(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(usuario.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuario);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
		return ResponseEntity.ok(serv.update(id, usuario));
	}

	@PutMapping(value = "/{id}/incrementar")
	public void incrementar(@PathVariable Long id) {
		serv.incrementarEmprestimos(id);
		ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}/decrementar")
	public ResponseEntity<Void> decrementar(@PathVariable Long id) {
		serv.decrementarEmprestimos(id);
		return ResponseEntity.noContent().build();
	}
}

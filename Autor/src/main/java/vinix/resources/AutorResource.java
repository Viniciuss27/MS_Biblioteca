package vinix.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import vinix.entities.Autor;
import vinix.services.AutorService;

@RestController
@RequestMapping(value = "/autores")
public class AutorResource {

	@Autowired
	private AutorService serv;

	public ResponseEntity<List<Autor>> findAll() {
		return ResponseEntity.ok().body(serv.findAll());
	}

	public ResponseEntity<Autor> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(serv.findById(id));
	}

	public ResponseEntity<List<Autor>> findByName(@PathVariable String name) {
		return ResponseEntity.ok().body(serv.findByName(name));
	}

	public ResponseEntity<List<Autor>> findByNacionalidade(@PathVariable String nac) {
		return ResponseEntity.ok().body(serv.findByNacionalidade(nac));
	}

	public ResponseEntity<Autor> insert(@RequestBody Autor autor) {
		autor = serv.insert(autor);
		URI uri = ServletUriComponentsBuilder.
				fromCurrentRequest().path("/{id}")
				.buildAndExpand(autor.getId()).toUri();
		return ResponseEntity.created(uri).body(autor);
	}

	public ResponseEntity<Void> delete(@PathVariable Long id) {
		serv.delete(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Autor> update(@PathVariable Long id, @RequestBody Autor obj) {
		return ResponseEntity.ok().body(serv.update(id, obj));
	}
}

package vinix.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import vinix.entities.Livro;
import vinix.services.LivroService;

@RestController
@RequestMapping(value = "/livros")
public class LivroResource {

    @Autowired
    private LivroService service;

    @GetMapping
    public ResponseEntity<List<Livro>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(value = "/isbn/{isbn}")
    public ResponseEntity<Livro> findByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(service.findByIsbn(isbn));
    }

    @GetMapping(value = "/categoria/{categoria}")
    public ResponseEntity<List<Livro>> findByCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.findByCategoria(categoria));
    }

    @GetMapping(value = "/disponiveis")
    public ResponseEntity<List<Livro>> findDisponiveis() {
        return ResponseEntity.ok(service.findDisponiveis());
    }

    @PostMapping
    public ResponseEntity<Livro> insert(@RequestBody Livro livro) {
        livro = service.insert(livro);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(livro.getId()).toUri();
        return ResponseEntity.created(uri).body(livro);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> update(@PathVariable Long id, @RequestBody Livro livro) {
        return ResponseEntity.ok(service.update(id, livro));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints internos chamados pelo ms-emprestimo via Feign
    @PutMapping(value = "/{id}/emprestar")
    public ResponseEntity<Void> emprestar(@PathVariable Long id) {
        service.emprestar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/devolver")
    public ResponseEntity<Void> devolver(@PathVariable Long id) {
        service.devolver(id);
        return ResponseEntity.noContent().build();
    }
}

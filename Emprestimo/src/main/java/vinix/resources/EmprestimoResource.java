package vinix.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import vinix.entities.Emprestimo;
import vinix.services.EmprestimoService;

@RestController
@RequestMapping(value = "/emprestimos")
public class EmprestimoResource {

    @Autowired
    private EmprestimoService service;

    @GetMapping
    public ResponseEntity<List<Emprestimo>> findAll() { 
    	return ResponseEntity.ok(service.findAll()); 
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Emprestimo> findById(@PathVariable Long id) { 
    	return ResponseEntity.ok(service.findById(id)); 
    }

    @GetMapping(value = "/usuario/{usuarioId}")
    public ResponseEntity<List<Emprestimo>> findByUsuario(@PathVariable Long usuarioId) { 
    	return ResponseEntity.ok(service.findByUsuario(usuarioId)); 
    }

    @GetMapping(value = "/livro/{livroId}")
    public ResponseEntity<List<Emprestimo>> findByLivro(@PathVariable Long livroId) { 
    	return ResponseEntity.ok(service.findByLivro(livroId)); 
    }

    @GetMapping(value = "/atrasados")
    public ResponseEntity<List<Emprestimo>> findAtrasados() { 
    	return ResponseEntity.ok(service.findAtrasados()); 
    }

    @PostMapping(value = "/livro/{livroId}/usuario/{usuarioId}")
    public ResponseEntity<Emprestimo> emprestar(@PathVariable Long livroId, @PathVariable Long usuarioId) {
        Emprestimo emp = service.emprestar(livroId, usuarioId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        		.path("/{id}").buildAndExpand(emp.getId()).toUri();
        return ResponseEntity.created(uri).body(emp);
    }

    @PutMapping(value = "/{id}/devolver")
    public ResponseEntity<Emprestimo> devolver(@PathVariable Long id) { 
    	return ResponseEntity.ok(service.devolver(id)); 
    }
}

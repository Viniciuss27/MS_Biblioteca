package vinix.resources;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vinix.entities.DTO.LivroDTO;
import vinix.services.LivroService;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroResource {

    private final LivroService service;

    @GetMapping
    public ResponseEntity<List<LivroDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LivroDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(value = "/isbn/{isbn}")
    public ResponseEntity<LivroDTO> findByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok().body(service.findByIsbn(isbn));
    }

    @GetMapping(value = "/disponiveis")
    public ResponseEntity<List<LivroDTO>> findDisponiveis() {
        return ResponseEntity.ok().body(service.findDisponiveis());
    }

    @PostMapping
    public ResponseEntity<LivroDTO> insert(@Validated @RequestBody LivroDTO dto) {
        LivroDTO newDto = service.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDto);
    }

    @PutMapping(value = "/{id}/emprestar")
    public ResponseEntity<Void> emprestar(@PathVariable Long id) {
        service.debitarEstoque(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/devolver")
    public ResponseEntity<Void> devolver(@PathVariable Long id) {
        service.creditarEstoque(id);
        return ResponseEntity.noContent().build();
    }
}
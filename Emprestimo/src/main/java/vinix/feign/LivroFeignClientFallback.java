package vinix.feign;

import org.springframework.http.ResponseEntity;

import vinix.DTO.LivroDTO;

public class LivroFeignClientFallback implements LivroFeignClient {
    @Override
    public ResponseEntity<LivroDTO> findById(Long id) {
        return ResponseEntity.ok(new LivroDTO(id, "Fallback", "N/A", 0));
    }
    @Override
    public ResponseEntity<Void> emprestar(Long id) { return ResponseEntity.noContent().build(); }
    @Override
    public ResponseEntity<Void> devolver(Long id) { return ResponseEntity.noContent().build(); }
}


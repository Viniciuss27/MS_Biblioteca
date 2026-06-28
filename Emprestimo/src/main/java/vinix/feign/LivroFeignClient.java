package vinix.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import vinix.DTO.LivroDTO;

@FeignClient(name = "livro", path = "/livros", fallback = LivroFeignClientFallback.class)
public interface LivroFeignClient {
	
    @GetMapping(value = "/{id}")
    ResponseEntity<LivroDTO> findById(@PathVariable Long id);
    
    @PutMapping(value = "/{id}/emprestar")
    ResponseEntity<Void> emprestar(@PathVariable Long id);
    
    @PutMapping(value = "/{id}/devolver")
    ResponseEntity<Void> devolver(@PathVariable Long id);
}

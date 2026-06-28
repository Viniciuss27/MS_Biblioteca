package vinix.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import vinix.DTO.UsuarioDTO;

@FeignClient(
		name = "ms-usuario",
		path = "/usuarios",
		fallback = UsuarioFeignClientFallback.class)
public interface UsuarioFeignClient {
	
    @GetMapping(value = "/{id}")
    ResponseEntity<UsuarioDTO> findById(@PathVariable Long id);
    
    @PutMapping(value = "/{id}/incrementar")
    ResponseEntity<Void> incrementar(@PathVariable Long id);
    
    @PutMapping(value = "/{id}/decrementar")
    ResponseEntity<Void> decrementar(@PathVariable Long id);
}

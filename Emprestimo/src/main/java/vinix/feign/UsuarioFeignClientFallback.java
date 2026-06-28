package vinix.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import vinix.DTO.UsuarioDTO;

@Component
public class UsuarioFeignClientFallback implements UsuarioFeignClient {
	
    @Override
    public ResponseEntity<UsuarioDTO> findById(Long id) {
        return ResponseEntity.ok(new UsuarioDTO(id, "Fallback", "fallback@email.com", 0, false));
    }
    
    @Override
    public ResponseEntity<Void> incrementar(Long id) {
    	return ResponseEntity.noContent().build();
    }
    
    @Override
    public ResponseEntity<Void> decrementar(Long id) {
    	return ResponseEntity.noContent().build();
    }
}



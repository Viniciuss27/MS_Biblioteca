package vinix.feignClients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import vinix.entities.Autor;

@Component
public class AutorFeignClientFallback implements AutorFeignClient{
	
	@Override
	public ResponseEntity<Autor> findById(Long id){
		return ResponseEntity.ok(new Autor(id, "Fallback", "Brasil", "vazia"));
	}

}

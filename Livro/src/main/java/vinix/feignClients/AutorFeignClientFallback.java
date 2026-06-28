package vinix.feignClients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import vinix.entities.DTO.AutorDTO;

@Component
public class AutorFeignClientFallback implements AutorFeignClient{
	
	@Override
	public ResponseEntity<AutorDTO> findById(Long id){
		return ResponseEntity.ok(new AutorDTO());
	}
}

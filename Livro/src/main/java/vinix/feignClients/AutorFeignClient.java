package vinix.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vinix.entities.DTO.AutorDTO;

@FeignClient(
		name = "autor",
		path = "/autores",
		fallback = AutorFeignClientFallback.class)
public interface AutorFeignClient {
	
	@GetMapping(value = "/{id}")
	ResponseEntity<AutorDTO> findById(@PathVariable Long id);

}

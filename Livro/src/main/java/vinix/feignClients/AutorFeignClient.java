package vinix.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vinix.entities.Autor;

@FeignClient(
		name = "autor",
		path = "/autores",
		fallback = AutorFeignClientFallback.class)
public interface AutorFeignClient {
	
	@GetMapping(value = "/{id}")
	ResponseEntity<Autor> findById(@PathVariable Long id);

}

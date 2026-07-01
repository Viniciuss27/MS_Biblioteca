package vinix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vinix.entities.Autor;
import java.util.List;


public interface AutorRepository extends JpaRepository<Autor, Long>{
    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
}

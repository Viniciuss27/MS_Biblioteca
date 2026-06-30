package vinix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vinix.entities.Livro;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByIsbn(String isbn);
    List<Livro> findByCategoria(String categoria);
    List<Livro> findByQuantidadeDisponivelGreaterThan(Integer quantidade);
}

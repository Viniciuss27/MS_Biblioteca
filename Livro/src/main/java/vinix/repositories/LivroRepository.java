package vinix.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vinix.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    Optional<Livro> findByIsbn(String isbn);
    
    List<Livro> findByCategoriaIgnoreCase(String categoria);
    
    @Query("SELECT l FROM Livro l WHERE l.quantidadeDisponivel > 0")
    List<Livro> findDisponiveis();
}

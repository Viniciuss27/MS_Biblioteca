package vinix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vinix.entities.Emprestimo;
import java.util.List;
import vinix.entities.enus.Status;



public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>{

	List<Emprestimo> findByLivroId(Long livroId);
	List<Emprestimo> findByUsuarioId(Long usuarioId);
	List<Emprestimo> findByStatus(Status status);
	
}

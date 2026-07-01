package vinix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vinix.entities.Usuario;
import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	List<Usuario> findByCpf(String cpf);
	List<Usuario> findByEmail(String email);
	List<Usuario> findByNome(String nome);
}

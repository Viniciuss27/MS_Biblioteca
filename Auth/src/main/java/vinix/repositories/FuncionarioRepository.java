package vinix.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import vinix.entities.Funcionario;
import java.util.Optional;
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByEmail(String email);
}

package vinix.services;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vinix.DTO.LivroDTO;
import vinix.DTO.UsuarioDTO;
import vinix.entities.Emprestimo;
import vinix.entities.enus.Status;
import vinix.feign.LivroFeignClient;
import vinix.feign.UsuarioFeignClient;
import vinix.repositories.EmprestimoRepository;
import vinix.services.exceptions.DatabaseException;
import vinix.services.exceptions.ResourceNotFoundException;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository repository;
    
    @Autowired
    private LivroFeignClient livroFeign;
    
    @Autowired
    private UsuarioFeignClient usuarioFeign;

    public List<Emprestimo> findAll() { return repository.findAll(); }

    public Emprestimo findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Emprestimo> findByUsuario(Long usuarioId) { 
    	return repository.findByUsuarioId(usuarioId); }
    
    public List<Emprestimo> findByLivro(Long livroId) { 
    	return repository.findByLivroId(livroId); }
    
    public List<Emprestimo> findAtrasados() { 
    	return repository.findByStatus(Status.ATRASADO); }

    public Emprestimo emprestar(Long livroId, Long usuarioId) {
    	
        //verifica estoque do livro
        LivroDTO livro = livroFeign.findById(livroId).getBody();
        if (livro == null || livro.getQuantidadeDisponivel() <= 0) {
            throw new DatabaseException("Livro sem exemplares disponíveis!");
        }
        
        //verifica limite de empréstimos do usuário
        UsuarioDTO usuario = usuarioFeign.findById(usuarioId).getBody();
        if (usuario == null || !usuario.getAtivo()) {
            throw new DatabaseException("Usuário inativo ou não encontrado!");
        }
        
        if (usuario.getEmprestimosAtivos() >= 3) {
            throw new DatabaseException("Usuário atingiu o limite de 3 empréstimos ativos!");
        }
        
        //reduz estoque via Feign
        livroFeign.emprestar(livroId);
        
        // incrementa empréstimos do usuário via Feign
        usuarioFeign.incrementar(usuarioId);
        
        //cria o empréstimo
        Emprestimo emp = new Emprestimo();
        emp.setLivroId(livroId);
        emp.setUsuarioId(usuarioId);
        emp.setDataEmprestimo(LocalDate.now());
        emp.setDataPrevistaDevolucao(LocalDate.now().plusDays(14));
        emp.setStatus(Status.ATIVO);
        return repository.save(emp);
    }

    public Emprestimo devolver(Long id) {
        Emprestimo emp = findById(id);
        if (emp.getStatus() == Status.DEVOLVIDO) {
            throw new DatabaseException("Este empréstimo já foi devolvido!");
        }
        // restaura estoque via Feign
        livroFeign.devolver(emp.getLivroId());
        
        // decrementa empréstimos do usuário via Feign
        usuarioFeign.decrementar(emp.getUsuarioId());
        emp.setDataDevolvido(LocalDate.now());
        emp.setStatus(Status.DEVOLVIDO);
        return repository.save(emp);
    }
}
package vinix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import vinix.entities.Usuario;
import vinix.repositories.UsuarioRepository;
import vinix.services.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository rep;

	public List<Usuario> findByCpf(String cpf) {
		return rep.findByCpf(cpf);
	}

	public List<Usuario> findByEmail(String email) {
		return rep.findByEmail(email);
	}

	public List<Usuario> findByNome(String nome) {
		return rep.findByNome(nome);
	}

	public List<Usuario> findAll() { 
		return rep.findAll(); 
		}
	
    public Usuario findById(Long id) { 
    	return rep.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)); 
    	}
    
    public Usuario insert(Usuario usuario) {
    	return rep.save(usuario);
    	}
    
    public Usuario update(Long id, Usuario obj) {
        try {
            Usuario entity = rep.getReferenceById(id);
            entity.setNome(obj.getNome()); 
            entity.setEmail(obj.getEmail());
            entity.setTelefone(obj.getTelefone());
            return rep.save(entity);
        } catch (EntityNotFoundException e) {
        	throw new ResourceNotFoundException(id); }
    }
    public void incrementarEmprestimos(Long id) {
        Usuario u = findById(id);
        u.setEmprestimosAtivos(u.getEmprestimosAtivos() + 1);
        rep.save(u);
    }
    public void decrementarEmprestimos(Long id) {
        Usuario u = findById(id);
        if (u.getEmprestimosAtivos() > 0) u.setEmprestimosAtivos(u.getEmprestimosAtivos() - 1);
        rep.save(u);
    }
}

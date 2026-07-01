package vinix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import vinix.entities.Autor;
import vinix.repositories.AutorRepository;
import vinix.services.exception.DatabaseException;
import vinix.services.exception.ResourceNotFoundException;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository rep;
	
	public List<Autor> findAll(){
		return rep.findAll();
	}
	
	public Autor findById(Long id) {
		return rep.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public List<Autor> findByName(String name) {
		return rep.findByNome(name);
	}
	
	public List<Autor> findByNacionalidade(String nac) {
		return rep.findByNome(nac);
	}
	
	public Autor insert(Autor autor) {
		return rep.save(autor);
	}
	
	public void delete(Long id) {
		try {
            if (!rep.existsById(id)) throw new ResourceNotFoundException(id);
            rep.deleteById(id);
        } catch (DataIntegrityViolationException e) { 
        	throw new DatabaseException(e.getMessage()); }
    }
		
	 public Autor update(Long id, Autor obj) {
        try {
            Autor entity = rep.getReferenceById(id);
            entity.setNome(obj.getNome());
            entity.setNacionalidade(obj.getNacionalidade());
            entity.setBiografia(obj.getBiografia());
            return rep.save(entity);
        } catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

}

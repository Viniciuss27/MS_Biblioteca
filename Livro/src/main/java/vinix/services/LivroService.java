package vinix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import vinix.entities.Livro;
import vinix.repositories.LivroRepository;
import vinix.services.exceptions.DataBaseException;
import vinix.services.exceptions.ResourceNotFoundException;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    public List<Livro> findAll() {
        return repository.findAll();
    }

    public Livro findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Livro findByIsbn(String isbn) {
        return repository.findByIsbn(isbn)
            .orElseThrow(() -> new ResourceNotFoundException(isbn));
    }

    public List<Livro> findByCategoria(String categoria) {
        return repository.findByCategoria(categoria);
    }

    public List<Livro> findDisponiveis() {
        return repository.findByQuantidadeDisponivelGreaterThan(0);
    }

    public Livro insert(Livro livro) {
        return repository.save(livro);
    }

    public Livro update(Long id, Livro obj) {
        try {
            Livro entity = repository.getReferenceById(id);
            entity.setTitulo(obj.getTitulo());
            entity.setIsbn(obj.getIsbn());
            entity.setEditora(obj.getEditora());
            entity.setAnoPublicacao(obj.getAnoPublicacao());
            entity.setCategoria(obj.getCategoria());
            entity.setQuantidadeTotal(obj.getQuantidadeTotal());
            entity.setAutores(obj.getAutores());
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            if (!repository.existsById(id)) throw new ResourceNotFoundException(id);
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    // Chamado pelo ms-emprestimo via Feign
    public void emprestar(Long id) {
        Livro livro = findById(id);
        if (livro.getQuantidadeDisponivel() <= 0) {
            throw new DataBaseException("Livro sem exemplares disponíveis!");
        }
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
        repository.save(livro);
    }

    // Chamado pelo ms-emprestimo via Feign
    public void devolver(Long id) {
        Livro livro = findById(id);
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        repository.save(livro);
    }
}

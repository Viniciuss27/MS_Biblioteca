package vinix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vinix.entities.Livro;
import vinix.entities.DTO.LivroDTO;
import vinix.repositories.LivroRepository;
import vinix.services.exceptions.DataBaseException;
import vinix.services.exceptions.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    @Transactional(readOnly = true)
    public List<LivroDTO> findAll() {
        return repository.findAll().stream().map(LivroDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LivroDTO findById(Long id) {
        Livro entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com o ID: " + id));
        return new LivroDTO(entity);
    }

    @Transactional(readOnly = true)
    public LivroDTO findByIsbn(String isbn) {
        Livro entity = repository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com o ISBN: " + isbn));
        return new LivroDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> findDisponiveis() {
        return repository.findDisponiveis().stream().map(LivroDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public LivroDTO insert(LivroDTO dto) {
        Livro entity = new Livro();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new LivroDTO(entity);
    }

    @Transactional
    public void debitarEstoque(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para baixa"));
        if (livro.getQuantidadeDisponivel() <= 0) {
            throw new DataBaseException("Estoque zerado para o livro selecionado.");
        }
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
        repository.save(livro);
    }

    @Transactional
    public void creditarEstoque(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para devolução"));
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        repository.save(livro);
    }

    private void copyDtoToEntity(LivroDTO dto, Livro entity) {
        entity.setTitulo(dto.getTitulo());
        entity.setIsbn(dto.getIsbn());
        entity.setEditora(dto.getEditora());
        entity.setAnoPublicacao(dto.getAnoPublicacao());
        entity.setCategoria(dto.getCategoria());
        entity.setQuantidadeTotal(dto.getQuantidadeTotal());
        entity.setQuantidadeDisponivel(dto.getQuantidadeDisponivel());
    }
}
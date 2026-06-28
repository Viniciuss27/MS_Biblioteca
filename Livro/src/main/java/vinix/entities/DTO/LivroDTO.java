package vinix.entities.DTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vinix.entities.Livro;

@Getter
@Setter
@NoArgsConstructor
public class LivroDTO {

    private Long id;
    
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    
    @NotBlank(message = "ISBN é obrigatório")
    private String isbn;
    
    private String editora;
    private Integer anoPublicacao;
    private String categoria;
    
    @PositiveOrZero
    private Integer quantidadeTotal;
    
    @PositiveOrZero
    private Integer quantidadeDisponivel;
    
    private Set<AutorDTO> autores = new HashSet<>();

    public LivroDTO(Livro entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.isbn = entity.getIsbn();
        this.editora = entity.getEditora();
        this.anoPublicacao = entity.getAnoPublicacao();
        this.categoria = entity.getCategoria();
        this.quantidadeTotal = entity.getQuantidadeTotal();
        this.quantidadeDisponivel = entity.getQuantidadeDisponivel();
        this.autores = entity.getAutores().stream().map(AutorDTO::new).collect(Collectors.toSet());
    }
}
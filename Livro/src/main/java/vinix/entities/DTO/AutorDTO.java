package vinix.entities.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vinix.entities.Autor;

@Getter
@Setter
@NoArgsConstructor
public class AutorDTO {
    private Long id;
    private String nome;
    private String nacionalidade;

    public AutorDTO(Autor entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.nacionalidade = entity.getNacionalidade();
    }
}
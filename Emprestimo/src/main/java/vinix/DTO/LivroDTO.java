package vinix.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer quantidadeDisponivel;
}

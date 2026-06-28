package vinix.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vinix.entities.enus.Status;

@Entity @Table(name = "tb_emprestimo")
@Data @NoArgsConstructor @AllArgsConstructor
public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private Long livroId;
	  private Long usuarioId;
	  private LocalDate dataEmprestimo;
	  private LocalDate dataPrevistaDevolucao;
	  private LocalDate dataDevolvido;
	  @Enumerated(EnumType.STRING)
	  private Status status; // ATIVO, DEVOLVIDO, ATRASADO
	}


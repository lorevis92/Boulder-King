package BoulderKing.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "eventi")
@Data
@NoArgsConstructor
public class Evento {
	@Id
	@GeneratedValue
	private UUID id;
	private String nomeEvento;
	private String località;
	@ManyToOne
	private Ente ente;
	private int puntiEvento;
	private LocalDate data;
	@OneToOne(mappedBy = "evento")
	private Classifica classifica;
	@ManyToMany
	@JoinTable(name = "evento_atleta", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "atleta_id"))
	private List<Atleta> partecipanti;
}

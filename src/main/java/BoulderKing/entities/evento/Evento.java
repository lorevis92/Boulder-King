package BoulderKing.entities.evento;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import BoulderKing.entities.classifica.Classifica;
import BoulderKing.entities.users.User;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Evento {
	@Id
	@GeneratedValue
	private UUID id;
	private String nomeEvento;
	private String località;
	@ManyToOne
	private User organizzatore;
	private int puntiEvento;
	private LocalDate data;
	private String immagineEvento;
	@OneToOne(mappedBy = "evento")
	private Classifica classifica;
	@ManyToMany
	@JoinTable(name = "evento_utente", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "atleta_id"))
	@JsonBackReference
	private List<User> partecipanti;
}

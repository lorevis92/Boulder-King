package BoulderKing.entities.evento;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import BoulderKing.Enum.EventoPassato;
import BoulderKing.entities.classifica.Classifica;
import BoulderKing.entities.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@JsonIgnoreProperties("partecipanti")
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
//	Ho deciso di gestire le foto degli eventi con un semplice URL inserito a mano nel DB dato chesto avendo problemi con le dimensioni dei file da caricare
//	@OneToOne(mappedBy = "eventoImmagine")
//	private Image immagineEvento;
	@Column(length = 100000)
	private String immagineEvento;
	String regione;
	String provincia;
	String citta;
	@OneToOne(mappedBy = "evento")
	private Classifica classifica;
	@ManyToMany
	@JoinTable(name = "evento_utente", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "atleta_id"))
	@JsonBackReference
	@JsonIgnore
	private List<User> partecipanti;
	@Enumerated(EnumType.STRING)
	private EventoPassato isPassed;

	protected void aggiungiPartecipante(User partecipante) {
		partecipanti.add(partecipante);
	}

	protected void hasPassed() {
		if (this.data.isAfter(LocalDate.now())) {
			this.isPassed = EventoPassato.PASSATO;
		} else
			this.isPassed = EventoPassato.FUTURO;
	}
}

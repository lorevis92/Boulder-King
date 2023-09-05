package BoulderKing.entities;

import java.util.List;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ente extends User{
	String nomeEnte;
	String numeroTelefonico;
	String emailContatto;
	String indirizzo;
	String informazioni;
	@Enumerated(EnumType.STRING)
	TipoEnte tipoEnte;
	@OneToMany(mappedBy = "ente")
	private List<Evento> listaEventi;
	
	// Costruttore
	public Ente(String nomeEnte, String numeroTelefono, String emailContatto, String indirizzo) {
		super();
		this.nomeEnte = nomeEnte;
		this.numeroTelefonico = numeroTelefono;
		this.emailContatto = emailContatto;
		this.indirizzo = indirizzo;
	}

}

package BoulderKing.entities;

import java.util.List;

import BoulderKing.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Atleta extends User {
	private String userName;
	private String name;
	private String surname;
	private int posizioneClassifica;
	private int puntiClassifica;
	@ManyToMany(mappedBy = "partecipanti")
	private List<Evento> listaEventi;

	// Costruttore
	public Atleta(String userName, String name, String surname) {
		super();
		this.userName = userName;
		this.name = name;
		this.surname = surname;
		this.posizioneClassifica = 0;
		this.puntiClassifica = 0;
		
		
	}

}

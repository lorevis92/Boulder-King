package BoulderKing.entities;

import BoulderKing.users.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Atleti extends User {
	private String userName;
	private String name;
	private String surname;
	private int posizioneClassifica;
	private int puntiClassifica;

//	private List<Evento> listaEventi;

	// Costruttore
	public Atleti(String userName, String name, String surname) {
		super();
		this.userName = userName;
		this.name = name;
		this.surname = surname;
		this.posizioneClassifica = 0;
		this.puntiClassifica = 0;
		
		
	}

}

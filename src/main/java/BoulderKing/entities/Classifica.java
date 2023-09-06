package BoulderKing.entities;

import java.util.UUID;

import BoulderKing.entities.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "classifica")
@Data
@NoArgsConstructor
public class Classifica {
	@Id
	@GeneratedValue
	private UUID id;
	@OneToOne
	@JoinColumn(name = "evento_id")
	private Evento evento;
	@ManyToOne
	@JoinColumn(name = "atleta01_id")
	private User posizione1;
	private int puntiPosizione01;
	@ManyToOne
	@JoinColumn(name = "atleta02_id")
	private User posizione2;
	private int puntiPosizione02;
	@ManyToOne
	@JoinColumn(name = "atleta03_id")
	private User posizione3;
	private int puntiPosizione03;
	@ManyToOne
	@JoinColumn(name = "atleta04_id")
	private User posizione4;
	private int puntiPosizione04;
	@ManyToOne
	@JoinColumn(name = "atleta05_id")
	private User posizione5;
	private int puntiPosizione05;
	@ManyToOne
	@JoinColumn(name = "atleta06_id")
	private User posizione6;
	private int puntiPosizione06;
	@ManyToOne
	@JoinColumn(name = "atleta07_id")
	private User posizione7;
	private int puntiPosizione07;
	@ManyToOne
	@JoinColumn(name = "atleta08_id")
	private User posizione8;
	private int puntiPosizione08;
	@ManyToOne
	@JoinColumn(name = "atleta09_id")
	private User posizione9;
	private int puntiPosizione09;
	@ManyToOne
	@JoinColumn(name = "atleta10_id")
	private User posizione10;
	private int puntiPosizione10;

	// Costruttore
	public Classifica(Evento evento) {
		this.evento = evento;
	}

}

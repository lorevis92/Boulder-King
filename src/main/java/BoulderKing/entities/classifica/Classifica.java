package BoulderKing.entities.classifica;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import BoulderKing.entities.evento.Evento;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Classifica {
	@Id
	@GeneratedValue
	private UUID id;
	@OneToOne
	@JoinColumn(name = "evento_id")
	private Evento evento;
	@ManyToOne
	@JoinColumn(name = "atleta01_id")
	private User posizione01;
	private int puntiPosizione01;
	@ManyToOne
	@JoinColumn(name = "atleta02_id")
	private User posizione02;
	private int puntiPosizione02;
	@ManyToOne
	@JoinColumn(name = "atleta03_id")
	private User posizione03;
	private int puntiPosizione03;
	@ManyToOne
	@JoinColumn(name = "atleta04_id")
	private User posizione04;
	private int puntiPosizione04;
	@ManyToOne
	@JoinColumn(name = "atleta05_id")
	private User posizione05;
	private int puntiPosizione05;
	@ManyToOne
	@JoinColumn(name = "atleta06_id")
	private User posizione06;
	private int puntiPosizione06;
	@ManyToOne
	@JoinColumn(name = "atleta07_id")
	private User posizione07;
	private int puntiPosizione07;
	@ManyToOne
	@JoinColumn(name = "atleta08_id")
	private User posizione08;
	private int puntiPosizione08;
	@ManyToOne
	@JoinColumn(name = "atleta09_id")
	private User posizione09;
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
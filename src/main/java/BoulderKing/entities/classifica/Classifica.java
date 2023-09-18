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
	private Integer puntiPosizione01 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta02_id")
	private User posizione02;
	private Integer puntiPosizione02 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta03_id")
	private User posizione03;
	private Integer puntiPosizione03 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta04_id")
	private User posizione04;
	private Integer puntiPosizione04 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta05_id")
	private User posizione05;
	private Integer puntiPosizione05 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta06_id")
	private User posizione06;
	private Integer puntiPosizione06 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta07_id")
	private User posizione07;
	private Integer puntiPosizione07 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta08_id")
	private User posizione08;
	private Integer puntiPosizione08 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta09_id")
	private User posizione09;
	private Integer puntiPosizione09 = 0;
	@ManyToOne
	@JoinColumn(name = "atleta10_id")
	private User posizione10;
	private Integer puntiPosizione10 = 0;

	// Costruttore
	public Classifica(Evento evento) {
		this.evento = evento;
	}

}

package BoulderKing.entities.caricaImmagini;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import BoulderKing.entities.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
	@Id
	@GeneratedValue
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "image", unique = false, nullable = false, length = 100000)
	private byte[] image;

	@OneToOne
	@JoinColumn(name = "immagineUser_id")
	@JsonIgnore
	private User userImmagine;

//	Ho deciso di gestire le foto degli eventi con un semplice URL inserito a mano nel DB dato chesto avendo problemi con le dimensioni dei file da caricare
//	@OneToOne
//	@JoinColumn(name = "immagineEvento_id")
//	@JsonIgnore
//	private Evento eventoImmagine;

}

package BoulderKing.entities.ente.payload;

import BoulderKing.Enum.TipoEnte;
import lombok.Getter;

@Getter
public class EnteUpdatePayload {
	private String nomeEnte;// --> name
	private String email; // --> non disponibile
	private String password;
	private String telefono; // --> non disponibile
	private String emailContatto;
	private String indirizzo; // --> "formatted_address": " -->rating \"rating\": 4.5,
	private String info;
	private TipoEnte tipoEnte;
	private String longitudine;
	private String latitudine;
	private String foto;
}

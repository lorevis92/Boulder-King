package BoulderKing.entities.ente.payload;

import BoulderKing.Enum.TipoEnte;
import lombok.Getter;

@Getter
public class EnteUpdatePayload {
	private String nomeEnte;
	private String email;
	private String password;
	private String telefono;
	private String emailContatto;
	private String indirizzo;
	private String info;
	private TipoEnte tipoEnte;
}

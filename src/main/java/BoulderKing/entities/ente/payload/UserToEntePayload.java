package BoulderKing.entities.ente.payload;

import BoulderKing.Enum.TipoEnte;
import lombok.Getter;

@Getter
public class UserToEntePayload {
	private String nomeEnte;
	private String telefono;
	private String emailContatto;
	private String indirizzo;
	private String info;
	private TipoEnte tipoEnte;
}

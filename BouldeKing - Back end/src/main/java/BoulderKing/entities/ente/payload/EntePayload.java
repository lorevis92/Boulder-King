package BoulderKing.entities.ente.payload;

import BoulderKing.Enum.TipoEnte;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntePayload {
//	@NotNull(message = "Il nome è obbligatorio")
//	@Size(min = 3, max = 30, message = "Nome deve avere minimo 3 caratteri, massimo 30")
	private String nomeEnte;
	@NotNull(message = "L'email è obbligatoria")
	@Email(message = "L'email inserita non è un indirizzo valido")
	private String email;
	@NotNull(message = "La password è obbligatoria")
	private String password;
	private TipoEnte tipoEnte;
}

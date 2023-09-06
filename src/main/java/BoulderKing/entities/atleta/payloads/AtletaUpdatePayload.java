package BoulderKing.entities.atleta.payloads;

import BoulderKing.Enum.TipoUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtletaUpdatePayload {
//	@NotNull(message = "Il nome è obbligatorio")
//	@Size(min = 3, max = 30, message = "Nome deve avere minimo 3 caratteri, massimo 30")
	private String name;
//	@NotNull(message = "Il cognome è obbligatorio")
	private String surName;
	private String userName;
	@NotNull(message = "L'email è obbligatoria")
	@Email(message = "L'email inserita non è un indirizzo valido")
	private String email;
	@NotNull(message = "La password è obbligatoria")
	private String password;
	private TipoUser tipoUser;
//	private Immagine profilo;
//	private Immagine copertina;

}

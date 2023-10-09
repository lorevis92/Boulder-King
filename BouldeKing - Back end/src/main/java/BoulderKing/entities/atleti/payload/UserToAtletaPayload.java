package BoulderKing.entities.atleti.payload;

import lombok.Getter;

@Getter
public class UserToAtletaPayload {
//	@NotNull(message = "Il nome è obbligatorio")
//	@Size(min = 3, max = 30, message = "Nome deve avere minimo 3 caratteri, massimo 30")
	private String name;
//	@NotNull(message = "Il cognome è obbligatorio")
	private String surName;
	private String userName;
}

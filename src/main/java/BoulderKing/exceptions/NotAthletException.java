package BoulderKing.exceptions;

import java.util.UUID;

@SuppressWarnings("serial")
public class NotAthletException extends RuntimeException {
	public NotAthletException(String message) {
		super(message);
	}

	public NotAthletException(UUID id) {
		super("Siamo spiacenti l'utente non è un atleta");
	}
}
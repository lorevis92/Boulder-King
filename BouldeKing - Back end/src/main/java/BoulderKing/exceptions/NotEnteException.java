package BoulderKing.exceptions;

import java.util.UUID;

@SuppressWarnings("serial")
public class NotEnteException extends RuntimeException {
	public NotEnteException(String message) {
		super(message);
	}

	public NotEnteException(UUID id) {
		super("Siamo spiacenti l'utente non è un Ente");
	}
}

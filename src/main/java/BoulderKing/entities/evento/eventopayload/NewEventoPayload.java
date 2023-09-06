package BoulderKing.entities.evento.eventopayload;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;

@Getter
public class NewEventoPayload {
	private String nomeEvento;
	private String località;
	private UUID organizzatore;
	private int puntiEvento;
	private LocalDate data;
}

package BoulderKing.entities.evento.eventopayload;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewEventoPayload {
	private String nomeEvento;
	private String zonaItalia;
	private UUID organizzatore;
	private String creatoreEvento;
	private int puntiEvento;
	private LocalDate data;
	private String immagineEvento;
	private String regione;
	private String provincia;
	private String citta;
	private String info;
	private String sito;
}

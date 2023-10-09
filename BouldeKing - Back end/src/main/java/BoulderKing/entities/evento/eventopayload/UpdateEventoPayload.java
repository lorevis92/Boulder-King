package BoulderKing.entities.evento.eventopayload;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventoPayload {
	private String nomeEvento;
	private String zonaItalia;
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

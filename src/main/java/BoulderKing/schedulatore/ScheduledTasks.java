package BoulderKing.schedulatore;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import BoulderKing.Enum.EventoPassato;
import BoulderKing.entities.evento.Evento;
import BoulderKing.entities.evento.EventoRepository;

public class ScheduledTasks {
	private final EventoRepository eventoRepository;

	// Inietta il repository mediante costruttore
	public ScheduledTasks(EventoRepository eventoRepository) {
		this.eventoRepository = eventoRepository;
	}

	// Schedula il task ogni giorno a mezzanotte
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateEventStatus() {
		// Trova tutti gli eventi con isPassed = FUTURO
		List<Evento> futureEvents = eventoRepository.findByIsPassed(EventoPassato.FUTURO);

		// Controlla e aggiorna lo stato di ogni evento
		for (Evento evento : futureEvents) {
			evento.hasPassed(); // Aggiorna lo stato dell'evento
			eventoRepository.save(evento); // Salva l'evento aggiornato nel database
		}
	}
}

package BoulderKing.entities.evento;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import BoulderKing.entities.evento.eventopayload.AggiungiPartecipantePayload;
import BoulderKing.entities.evento.eventopayload.NewEventoPayload;
import BoulderKing.entities.users.User;

@EnableMethodSecurity
@RestController
@RequestMapping("/eventi")
public class EventoController {
	private final EventoService eventoServ;

	@Autowired
	public EventoController(EventoService eventoServ) {
		this.eventoServ = eventoServ;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Evento saveEvento(@RequestBody @Validated NewEventoPayload body) {
		Evento created = eventoServ.create(body);
		return created;
	}

	@GetMapping
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<Evento> getEventi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return eventoServ.find(page, size, sortBy);
	}

	@GetMapping("/{eventoId}")
	public Evento findById(@PathVariable UUID eventoId) {
		return eventoServ.findById(eventoId);

	}

	@PutMapping("/{eventoId}")
	public Evento updateUser(@PathVariable UUID eventoId, @RequestBody NewEventoPayload body) {
		return eventoServ.findByIdAndUpdate(eventoId, body);
	}

	@DeleteMapping("/{eventoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID eventoId) {
		eventoServ.findByIdAndDelete(eventoId);
	}

// Filtraggi avanzati
	// Eventi organizzati da un certo ente
	@GetMapping("/organizzatore/{organizzatoreId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<Evento> FiltraEventiPerPrganizzatore(@PathVariable UUID organizzatoreId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return eventoServ.findByOrganizzatore(organizzatoreId, page, size, sortBy);
	}

	// Eventi filtrati per nome evento
	@GetMapping("/nome/{nomeEvento}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<Evento> FiltraEventiPerNome(@PathVariable String nomeEvento, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return eventoServ.findByNome(nomeEvento, page, size, sortBy);
	}
	
	//Aggiungi partecipanti all'evento
	@PostMapping("/{idEvento}/partecipanti")
	public ResponseEntity<?> aggiungiPartecipante(@PathVariable UUID idEvento,
			@RequestBody AggiungiPartecipantePayload body) {
		try {
			Evento evento = eventoServ.aggiungiPartecipante(idEvento, body.getIdUtente());
			return new ResponseEntity<>(evento, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// Lista partecipanti ad un evento
	@GetMapping("/partecipanti/{idEvento}")
	public ResponseEntity<List<User>> getPartecipantiEvento(@PathVariable UUID idEvento) {
		try {
			Evento evento = eventoServ.findById(idEvento);
			List<User> partecipanti = evento.getPartecipanti();
			return new ResponseEntity<>(partecipanti, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}

}
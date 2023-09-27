package BoulderKing.entities.evento;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
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
	public ResponseEntity<Page<User>> getPartecipantiEvento(@PathVariable UUID idEvento,
	        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "id") String sortBy) {
	    try {
	        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	        Page<User> partecipanti = eventoServ.getPartecipantiPaginated(idEvento, pageable);

			HttpHeaders headers = new HttpHeaders();
			headers.add("X-Total-Count", String.valueOf(partecipanti.getTotalElements())); // Aggiungi il numero totale
																							// di elementi all'header

			return ResponseEntity.ok().headers(headers).body(partecipanti);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}


	// Id di un evento sapendo la classifica associata
	@GetMapping("/classifica/{classificaId}")
	public ResponseEntity<Evento> getEventoByClassificaId(@PathVariable UUID classificaId) {
		Evento evento = eventoServ.getEventoByClassificaId(classificaId);
		return ResponseEntity.ok(evento);
	}

	// Ricerca con tutti i filtri
	@GetMapping("/search")
	public Page<Evento> findByFilters(@RequestParam(required = false) String nomeEvento,
			@RequestParam(required = false) String nomeEnte,
			@RequestParam(required = false) String regione, @RequestParam(required = false) String provincia,
			@RequestParam(required = false) String citta, @RequestParam(required = false) String zonaItalia,
			@RequestParam(required = false) String isPassed,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return eventoServ.findByFilters(nomeEvento, nomeEnte, regione, provincia, citta, zonaItalia, isPassed, page,
				size, sortBy);
	}

}
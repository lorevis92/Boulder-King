package BoulderKing.entities.evento;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import BoulderKing.entities.evento.eventopayload.NewEventoPayload;

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
}
package BoulderKing.entities.classifica;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

import BoulderKing.entities.atleti.AtletaService;
import BoulderKing.entities.classifica.payload.UpdateClassificaPayload;
import BoulderKing.entities.users.User;

@EnableMethodSecurity
@RestController
@RequestMapping("/classifiche")
public class ClassificaController {
	private final ClassificaService classificaServ;
	private final AtletaService atletaServ;

	@Autowired
	public ClassificaController(ClassificaService classificaServ, AtletaService atletaServ) {
		this.classificaServ = classificaServ;
		this.atletaServ = atletaServ;
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Classifica saveClassifica() {
		Classifica created = classificaServ.create();
		return created;
	}

	@GetMapping
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<Classifica> getEventi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return classificaServ.find(page, size, sortBy);
	}

	@GetMapping("/{classificaId}")
	public Classifica findById(@PathVariable UUID classificaId) {
		return classificaServ.findById(classificaId);

	}

	@PutMapping("/{classificaId}")
	public Classifica updateUser(@PathVariable UUID classificaId, @RequestBody UpdateClassificaPayload body) {
		return classificaServ.findByIdAndUpdate(classificaId, body);
	}

	@DeleteMapping("/{classificaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID classificaId) {
		classificaServ.findByIdAndDelete(classificaId);
	}

// Filtri avanzati
	// Filtra Classifica per Evento
	@GetMapping("/evento/{eventoId}")
	public Classifica findByEvento(@PathVariable UUID eventoId) {
		return classificaServ.findByEvento(eventoId);
	}

	// Find classifiche in cui un utente è arrivato primo
	@GetMapping("/elencoPrimiPosti/{atletaId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<Classifica> getClassifichePrimoClassificato(@PathVariable UUID atletaId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return classificaServ.findByPrimoPosto(atletaId, page, size, sortBy);
	}

	// Nuomero di volte che un atleta è arrivato primo
	@GetMapping("/countPrimoPosto/{atletaId}")
	public int countPrimoPosto(@PathVariable UUID atletaId) {
		return classificaServ.countPrimiPosti(atletaId);
	}

	// Ricerca con tutti i filtri
	@GetMapping("/search")
	public Page<User> findByFilters(@RequestParam(required = false) String nomeEnte,
			@RequestParam(required = false) String regione, @RequestParam(required = false) String provincia,
			@RequestParam(required = false) String citta, @RequestParam(required = false) String zonaItalia,
			@RequestParam(required = false) String tipoEnte, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortOrder) {
		return atletaServ.findByFilters(nomeEnte, regione, provincia, citta, zonaItalia, tipoEnte, page, size, sortBy,
				sortOrder);
	}

}
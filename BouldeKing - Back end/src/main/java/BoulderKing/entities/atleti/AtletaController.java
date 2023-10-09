package BoulderKing.entities.atleti;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import BoulderKing.entities.atleti.payload.AtletaPayload;
import BoulderKing.entities.atleti.payload.UserToAtletaPayload;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersService;

@EnableMethodSecurity
@RestController
@RequestMapping("/atleti")
public class AtletaController {
	private final AtletaService atletaServ;
	private final UsersService userServ;
	@Autowired
	public AtletaController(AtletaService atletaServ, UsersService userServ) {
		this.atletaServ = atletaServ;
		this.userServ = userServ;
	}


	@Autowired
	PasswordEncoder bcrypt;

// CRUD di base

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody @Validated AtletaPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		body.setEmail(bcrypt.encode(body.getEmail()));
		User created = atletaServ.create(body);
		return created;
	}

	@GetMapping
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getAtleta(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return atletaServ.find(page, size, sortBy);
	}



	@GetMapping("/{userId}")
	public User findById(@PathVariable UUID userId) {
		return atletaServ.findById(userId);
	}

	@PutMapping("/{userId}")
	public User updateUser(@PathVariable UUID userId, @RequestBody AtletaPayload body) {
		return atletaServ.findByIdAndUpdate(userId, body);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		atletaServ.findByIdAndDelete(userId);
	}

	// Update User to Atleta
	@PutMapping("/trasformazione/{userId}")
	public User updateUserToAtleta(@PathVariable UUID userId, @RequestBody UserToAtletaPayload body) {
		return userServ.findByIdAndUpdateToAtleta(userId, body);
	}

// Filtri avanzati

	// Filtra Atleta per nome
	@GetMapping("/nome/{parteNome}")
	public Page<User> findByParteNome(@PathVariable String parteNome, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return atletaServ.findByName(parteNome, page, size, sortBy);
	}

	// Filtra Atleta per Cognome
	@GetMapping("/cognome/{parteCognome}")
	public Page<User> findByParteCognome(@PathVariable String parteCognome, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return atletaServ.findBySurname(parteCognome, page, size, sortBy);
	}

//	// Filtra Atleta per UserName
//	@GetMapping("/userName/{parteUserName}")
//	public Page<User> findByParteUserName(@PathVariable String parteUserName,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
//		return atletaServ.findByUserName(parteUserName, page, size, sortBy);
//	}

	// Filtra atleta per posizione classifica
	@GetMapping("/posizioneEsatta/{posizione}")
	public User findById(@PathVariable int posizione) {
		return atletaServ.findByPosizioneClassifica(posizione);
	}

	// Filtra Atleta per posizione minima in classifica
	@GetMapping("/posizioneMinima/{posizione}")
	public Page<User> findByPosizioneMinima(@PathVariable int posizione, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return atletaServ.findByPosizioneClassificaMinima(posizione, page, size, sortBy);
	}

	// Filtra Atleta per posizione minima in classifica
	@GetMapping("/punteggioMinimo/{punteggio}")
	public Page<User> findByPunteggioMinimo(@PathVariable int punteggio, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return atletaServ.findByPunteggioClassificaMinimo(punteggio, page, size, sortBy);
	}

	// Ordina per punteggio
	@GetMapping("/classifica")
	public Page<User> ordinaByPunteggio(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return atletaServ.OrdinaByPunteggio(page, size, sortBy);
	}
}
package BoulderKing.entities.ente;

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

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.ZonaItalia;
import BoulderKing.entities.ente.payload.EntePayload;
import BoulderKing.entities.ente.payload.EnteUpdatePayload;
import BoulderKing.entities.ente.payload.UserToEntePayload;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersService;

@EnableMethodSecurity
@RestController
@RequestMapping("/enti")
public class EnteController {
	private final EnteService enteServ;
	private final UsersService userServ;

	@Autowired
	public EnteController(EnteService enteServ, UsersService userServ) {
		this.enteServ = enteServ;
		this.userServ = userServ;
	}

	@Autowired
	PasswordEncoder bcrypt;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody @Validated EntePayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		User created = enteServ.create(body);
		return created;
	}

	@GetMapping
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getEnte(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return enteServ.find(page, size, sortBy);
	}

	@GetMapping("/{userId}")
	public User findById(@PathVariable UUID userId) {
		return enteServ.findById(userId);
	}

	@PutMapping("/{userId}")
	public User updateUser(@PathVariable UUID userId, @RequestBody EnteUpdatePayload body) {
		return enteServ.findByIdAndUpdate(userId, body);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		enteServ.findByIdAndDelete(userId);
	}

	// Update User to Ente
	@PutMapping("/trasformazione/{userId}")
	public User updateUserToEnte(@PathVariable UUID userId, @RequestBody UserToEntePayload body) {
		return userServ.findByIdAndUpdateToEnte(userId, body);
	}

	// Ricerca per nomeEnte
	@GetMapping("/nome/{nomeEnte}")
	public Page<User> findByNomeEnte(@PathVariable String nomeEnte, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return enteServ.findByNomeEnte(nomeEnte, page, size, sortBy);
	}

	// Ricerca per nomeEnte
	@GetMapping("/tipo/{tipoEnte}")
	public Page<User> findByNomeEnte(@PathVariable TipoEnte tipoEnte, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return enteServ.findByTipoEnte(tipoEnte, page, size, sortBy);
	}

	// Ricerca con tutti i filtri
	@GetMapping("/search")
	public Page<User> findByFilters(@RequestParam(required = false) String nomeEnte,
			@RequestParam(required = false) String regione, @RequestParam(required = false) String provincia,
			@RequestParam(required = false) String citta, @RequestParam(required = false) ZonaItalia zonaItalia,
			@RequestParam(required = false) TipoEnte tipoEnte, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return enteServ.findByFilters(nomeEnte, regione, provincia, citta, zonaItalia, tipoEnte, page, size, sortBy);
	}
}

package BoulderKing.entities.atleta;

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

import BoulderKing.entities.atleta.payloads.AtletaRequestPayload;
import BoulderKing.entities.atleta.payloads.AtletaUpdatePayload;
import BoulderKing.entities.users.User;

@EnableMethodSecurity
@RestController
@RequestMapping("/atleti")
public class AtletaController {
	private final AtletaService atletaServ;

	@Autowired
	public AtletaController(AtletaService atletaServ) {
		this.atletaServ = atletaServ;
	}

	@Autowired
	PasswordEncoder bcrypt;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Atleta saveUser(@RequestBody @Validated AtletaRequestPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		body.setEmail(bcrypt.encode(body.getEmail()));
		Atleta created = atletaServ.create(body);
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
	public User updateUser(@PathVariable UUID userId, @RequestBody AtletaUpdatePayload body) {
		return atletaServ.findByIdAndUpdate(userId, body);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		atletaServ.findByIdAndDelete(userId);
	}
}

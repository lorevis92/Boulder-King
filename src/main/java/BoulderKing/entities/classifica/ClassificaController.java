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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@EnableMethodSecurity
@RestController
@RequestMapping("/classifiche")
public class ClassificaController {
	private final ClassificaService classificaServ;

	@Autowired
	public ClassificaController(ClassificaService classificaServ) {
		this.classificaServ = classificaServ;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Classifica saveEvento() {
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

//	@PutMapping("/{classificaId}")
//	public Classifica updateUser(@PathVariable UUID classificaId, @RequestBody NewClassificaPayload body) {
//		return classificaServ.findByIdAndUpdate(classificaId, body);
//	}

	@DeleteMapping("/{classificaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID classificaId) {
		classificaServ.findByIdAndDelete(classificaId);
	}
}
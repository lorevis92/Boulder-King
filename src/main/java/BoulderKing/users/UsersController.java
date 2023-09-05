package BoulderKing.users;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import BoulderKing.users.payloads.UserRequestPayload;

@EnableMethodSecurity
@RestController
@RequestMapping("/users")
public class UsersController {
	private final UsersService usersServ;

	@Autowired
	public UsersController(UsersService usersServ) {
		this.usersServ = usersServ;
	}

	@GetMapping
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return usersServ.find(page, size, sortBy);
	}

	@GetMapping("/{userId}")
	public User findById(@PathVariable UUID userId) {
		return usersServ.findById(userId);

	}

	@PutMapping("/{userId}")
	public User updateUser(@PathVariable UUID userId, @RequestBody UserRequestPayload body) {
		return usersServ.findByIdAndUpdate(userId, body);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		usersServ.findByIdAndDelete(userId);
	}

}

package BoulderKing.users;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.exceptions.BadRequestException;
import BoulderKing.exceptions.NotFoundException;
import BoulderKing.users.payloads.UserRequestPayload;

@Service
public class UsersService {
	private final UsersRepository usersRepo;

	@Autowired
	public UsersService(UsersRepository usersRepo) {
		this.usersRepo = usersRepo;
	}

	public User create(UserRequestPayload body) {
		// check if email already in use
		usersRepo.findByEmail(body.getEmail()).ifPresent(user -> {
			throw new BadRequestException("L'email è già stata utilizzata");
		});
		User newUser = new User(body.getName(), body.getSurname(), body.getEmail(), body.getPassword());
		return usersRepo.save(newUser);
	}

	public Page<User> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort)); // (numero di pagina, numero di elementi per
																		// pagina, nome del campo per cui sortare)
		return usersRepo.findAll(pageable);
	}

	public User findById(UUID id) throws NotFoundException {
		return usersRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public User findByIdAndUpdate(UUID id, UserRequestPayload body) throws NotFoundException {
		User found = this.findById(id);
		found.setEmail(body.getEmail());
		found.setName(body.getName());
		found.setSurname(body.getSurname());

		return usersRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		usersRepo.delete(found);
	}

	public User findByEmail(String email) {
		return usersRepo.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato"));
	}
}

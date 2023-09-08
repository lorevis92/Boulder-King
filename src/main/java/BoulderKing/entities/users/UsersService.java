package BoulderKing.entities.users;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.Enum.TipoUser;
import BoulderKing.entities.atleti.payload.UserToAtletaPayload;
import BoulderKing.entities.ente.payload.UserToEntePayload;
import BoulderKing.entities.users.payloads.UserRequestPayload;
import BoulderKing.exceptions.BadRequestException;
import BoulderKing.exceptions.NotFoundException;

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
		User newUser = new User(body.getEmail(), body.getPassword());
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
		found.setPassword(body.getPassword());
		return usersRepo.save(found);
	}

	// Update User to Atleta
	public User findByIdAndUpdateToAtleta(UUID id, UserToAtletaPayload body) throws NotFoundException {
		User found = this.findById(id);
		found.setName(body.getName());
		found.setSurname(body.getSurName());
		found.setUserName(body.getUserName());
		found.setTipoUser(TipoUser.ATLETA);
		return usersRepo.save(found);
	}

	// Update User to Ente
	public User findByIdAndUpdateToEnte(UUID id, UserToEntePayload body) throws NotFoundException {
		User found = this.findById(id);
		found.setNomeEnte(body.getNomeEnte());
		found.setNumeroTelefonico(body.getTelefono());
		found.setEmailContatto(body.getEmailContatto());
		found.setIndirizzo(body.getIndirizzo());
		found.setInformazioni(body.getInfo());
		found.setTipoUser(TipoUser.ENTE);
		found.setTipoEnte(body.getTipoEnte());
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

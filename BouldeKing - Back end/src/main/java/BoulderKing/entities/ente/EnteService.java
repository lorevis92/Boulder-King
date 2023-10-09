package BoulderKing.entities.ente;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.TipoUser;
import BoulderKing.entities.ente.payload.EntePayload;
import BoulderKing.entities.ente.payload.EnteUpdatePayload;
import BoulderKing.entities.users.Role;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersRepository;
import BoulderKing.exceptions.BadRequestException;
import BoulderKing.exceptions.NotEnteException;
import BoulderKing.exceptions.NotFoundException;

@Service
public class EnteService {
	private final UsersRepository usersRepo;

	@Autowired
	public EnteService(UsersRepository usersRepo) {
		this.usersRepo = usersRepo;
	}

	public User create(EntePayload body) {
		// check if email already in use
		usersRepo.findByEmail(body.getEmail()).ifPresent(atleta -> {
			throw new BadRequestException("L'email è già stata utilizzata");
		});
		User nuovoEnte = new User();
		nuovoEnte.setNomeEnte(body.getNomeEnte());
		nuovoEnte.setEmail(body.getEmail());
		nuovoEnte.setPassword(body.getPassword());
		nuovoEnte.setTipoEnte(body.getTipoEnte());
		nuovoEnte.setRole(Role.USER);
		nuovoEnte.setTipoUser(TipoUser.ENTE);
		return usersRepo.save(nuovoEnte);
	}

	// Lista degli Enti
	public Page<User> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findByTipoUser(TipoUser.ENTE, pageable);
	}

	// Ricerca atleta per Id
	public User findById(UUID id) throws NotFoundException, NotEnteException {
		Optional<User> optionalUser = usersRepo.findById(id);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getTipoUser() == TipoUser.ENTE) {
				return user;
			} else {
				throw new NotEnteException(id);
			}
		} else {
			throw new NotFoundException(id);
		}
	}

	public User findByIdAndUpdate(UUID id, EnteUpdatePayload body) throws NotFoundException {
		User ente = this.findById(id);

		ente.setNomeEnte(body.getNomeEnte());
		ente.setNumeroTelefonico(body.getTelefono());
		ente.setEmailContatto(body.getEmailContatto());
		ente.setIndirizzo(body.getIndirizzo());
		ente.setInformazioni(body.getInfo());
		ente.setEmail(body.getEmail());
		ente.setPassword(body.getPassword());
		ente.setTipoEnte(body.getTipoEnte());
		return usersRepo.save(ente);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		usersRepo.delete(found);
	}

// Ricerche avanzate

	// Ricerca per nomeEnte
	public Page<User> findByNomeEnte(String nomeEnte, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findByNomeEnteContainingIgnoreCase(nomeEnte, pageable);
	}

	// Ricerca per TipoEnte
	public Page<User> findByTipoEnte(TipoEnte tipoEnte, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findByTipoEnte(tipoEnte, pageable);
	}

	// Filtraggio
	public Page<User> findByFilters(String nomeEnte, String regione, String provincia, String citta,
			String zonaItalia, String tipoEnte, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo
				.findByFilters(
				nomeEnte, regione, provincia, citta, zonaItalia, tipoEnte, pageable);
	}
}
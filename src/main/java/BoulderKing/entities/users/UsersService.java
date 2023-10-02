package BoulderKing.entities.users;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.TipoUser;
import BoulderKing.Enum.ZonaItalia;
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
		User newUser = new User();
		newUser.setEmail(body.getEmail());
		newUser.setPassword(body.getPassword());
		newUser.setName(body.getName());
		newUser.setSurname(body.getSurname());
		newUser.setNomeEnte(body.getNomeEnte());
		newUser.setTipoUser(convertiStringaInTipoUser(body));
		newUser.setRole(Role.USER);
		return usersRepo.save(newUser);
	}

	private TipoUser convertiStringaInTipoUser(UserRequestPayload body) {
		if ("ENTE".equals(body.getTipoUser())) {
			return TipoUser.ENTE;
		} else
			return TipoUser.ATLETA;
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
		found.setName(body.getName());
		found.setSurname(body.getSurname());
		found.setNomeEnte(body.getNomeEnte());
		found.setUserName(body.getUserName());
		found.setNumeroTelefonico(body.getNumeroTelefonico());
		found.setIndirizzo(body.getIndirizzo());
		found.setOrari(body.getOrari());
		found.setInformazioni(body.getInformazioni());
		found.setLongitudine(body.getLongitudine());
		found.setLatitudine(body.getLatitudine());
		found.setCitta(body.getCitta());
		found.setProvincia(body.getProvincia());
		found.setRegione(body.getRegione());
		if (body.getZonaItalia() != null) {
			String zonaItalia = body.getZonaItalia().toUpperCase();
			if (zonaItalia.equals("NORD")) {
				found.setZonaItalia(ZonaItalia.NORD);
			} else if (zonaItalia.equals("CENTRO")) {
				found.setZonaItalia(ZonaItalia.CENTRO);
			}
			else if (zonaItalia.equals("SUD")) {
				found.setZonaItalia(ZonaItalia.SUD);
			}
		}
		if (body.getTipoEnte() != null) {
			String tipoEnte = body.getTipoEnte().toUpperCase();
			if (tipoEnte.equals("FALESIA")) {
				found.setTipoEnte(TipoEnte.FALESIA);
			} else if (tipoEnte.equals("PALESTRA")) {
				found.setTipoEnte(TipoEnte.PALESTRA);
			}
		}
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

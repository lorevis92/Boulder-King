package BoulderKing.entities.atleti;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.Enum.TipoUser;
import BoulderKing.entities.atleti.payload.AtletaPayload;
import BoulderKing.entities.users.Role;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersRepository;
import BoulderKing.exceptions.BadRequestException;
import BoulderKing.exceptions.NotAthletException;
import BoulderKing.exceptions.NotFoundException;

@Service
public class AtletaService {
	private final UsersRepository usersRepo;

	@Autowired
	public AtletaService(UsersRepository usersRepo) {
		this.usersRepo = usersRepo;
	}

//CRUD Base

	// Nuovo atleta
	public User create(AtletaPayload body) {
		// check if email already in use
		usersRepo.findByEmail(body.getEmail()).ifPresent(atleta -> {
			throw new BadRequestException("L'email è già stata utilizzata");
		});
		User nuovoAtleta = new User();
		nuovoAtleta.setName(body.getName());
		nuovoAtleta.setSurname(body.getSurName());
		nuovoAtleta.setUserName(body.getUserName());
		nuovoAtleta.setEmail(body.getEmail());
		nuovoAtleta.setPassword(body.getPassword());
		nuovoAtleta.setRole(Role.USER);
		nuovoAtleta.setTipoUser(TipoUser.ATLETA);
		nuovoAtleta.setNumeroPartecipazioni(0);
		nuovoAtleta.setNumeroPodi(0);
		nuovoAtleta.setPosizioneClassifica(0);
		nuovoAtleta.setPrimoPosto(0);
		nuovoAtleta.setPuntiClassifica(0);

		return usersRepo.save(nuovoAtleta);
	}


	//Lista degli atleti
	public Page<User> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findByTipoUser(TipoUser.ATLETA, pageable);
	}
	
	//Ricerca atleta per Id
	public User findById(UUID id) throws NotFoundException, NotAthletException {
		Optional<User> optionalUser = usersRepo.findById(id);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getTipoUser() == TipoUser.ATLETA) {
				return user;
			} else {
				throw new NotAthletException(id);
			}
		} else {
			throw new NotFoundException(id);
		}
	}


	public User findByIdAndUpdate(UUID id, AtletaPayload body) throws NotFoundException {
		User atleta = this.findById(id);

		atleta.setEmail(body.getEmail());
		atleta.setName(body.getName());
		atleta.setSurname(body.getSurName());
		atleta.setPassword(body.getPassword());
		atleta.setUserName(body.getUserName());
		return usersRepo.save(atleta);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		usersRepo.delete(found);
	}

//Filtri avanzati

	// Find by parte nome
	public Page<User> findByName(String parteNome, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findByNameContainingIgnoreCase(parteNome, pageable);
	}

	// Find by parte Cognome
	public Page<User> findBySurname(String parteCognome, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findBySurnameContainingIgnoreCase(parteCognome, pageable);
	}

//	// Find by parte UserName
//	public Page<User> findByUserName(String parteUserName, int page, int size, String sort) {
//		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
//		return usersRepo.findByUserNameContainingIgnoreCase(parteUserName, pageable);
//	}

	// Find atleti con un punteggio in classifica superiore ad un dato punteggio
	public Page<User> findByPunteggioMaggiore(int puntiClassifica, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findByPuntiClassificaGreaterThan(puntiClassifica, pageable);
	}

	// Find atleti con una determinata posizione in classifica
	public User findByPosizioneClassifica(int posizione) throws BadRequestException {
		Optional<User> optionalUser = usersRepo.findByPosizioneClassifica(posizione);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
				return user;
		} else {
			throw new BadRequestException("Non ci sono Atleti con queta posizione in classifica");
		}
	}

	// Find atleti con la posizione in classifica da un punto in su
	public Page<User> findByPosizioneClassificaMinima(int posizioneClassifica, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findByPosizioneClassificaLessThan(posizioneClassifica, pageable);
	}

	// Find atleti con un punteggio in classifica minimo
	public Page<User> findByPunteggioClassificaMinimo(int posizioneClassifica, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findByPuntiClassificaGreaterThan(posizioneClassifica, pageable);
	}

	// Ordina gli atleti per punteggio classifica
	public Page<User> OrdinaByPunteggio(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return usersRepo.findOrderedAtleti(pageable);
	}

	// Trova gli atleti applicando filtri generali
	public Page<User> findByFilters(String nomeEnte, String regione, String provincia, String citta, String zonaItalia,
			String tipoEnte, int page, int size, String sort, String sortOrder) {
		if ("desc".equalsIgnoreCase(sortOrder)) {
			Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc(sort)));
			return usersRepo.findByFiltersAtleti(nomeEnte, regione, provincia, citta, zonaItalia, tipoEnte, pageable);
		} else {
			Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));
			return usersRepo.findByFiltersAtleti(nomeEnte, regione, provincia, citta, zonaItalia, tipoEnte, pageable);
		}

	}

}
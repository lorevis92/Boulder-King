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

}
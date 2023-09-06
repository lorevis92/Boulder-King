package BoulderKing.entities.classifica;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.entities.users.UsersService;
import BoulderKing.exceptions.NotFoundException;

@Service
public class ClassificaService {
	private final ClassificaRepository classificaRepo;

	@Autowired
	public ClassificaService(ClassificaRepository classificaRepo) {
		this.classificaRepo = classificaRepo;
	}

	@Autowired
	public UsersService userServ;

	public Classifica create() {
		Classifica newClassifica = new Classifica();
		return classificaRepo.save(newClassifica);
	}

	public Page<Classifica> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort)); // (numero di pagina, numero di elementi per
																		// pagina, nome del campo per cui sortare)
		return classificaRepo.findAll(pageable);
	}

	public Classifica findById(UUID classificaId) throws NotFoundException {
		return classificaRepo.findById(classificaId).orElseThrow(() -> new NotFoundException(classificaId));
	}

//	public Classifica findByIdAndUpdate(UUID classificaId, NewClassificaPayload body) throws NotFoundException {
//		Classifica found = this.findById(classificaId);
//		found.setNomeEvento(body.getNomeEvento());
//		found.setLocalità(body.getLocalità());
//		found.setPuntiEvento(body.getPuntiEvento());
//		found.setData(body.getData());
//		found.setOrganizzatore(userServ.findById(body.getOrganizzatore()));
//		return eventoRepo.save(found);
//	}

	public void findByIdAndDelete(UUID classificaId) throws NotFoundException {
		Classifica found = this.findById(classificaId);
		classificaRepo.delete(found);
	}
}

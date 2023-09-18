package BoulderKing.entities.classifica;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.entities.classifica.payload.UpdateClassificaPayload;
import BoulderKing.entities.evento.EventoService;
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

	@Autowired
	public EventoService eventoServ;

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

	// Così sto settando io il punteggio che va al vincitore per ogni gara
	// indipendentemente da quello che decidono gli organizatori. Se voglio che gli
	// organizzatori decidino il punteggio
	// allora devo cambiare il found.setPuntiPosizione01(150); in
	// found.setPuntiPosizione01(body.getPuntiPosizione01());
	public Classifica findByIdAndUpdate(UUID classificaId, UpdateClassificaPayload body) throws NotFoundException {
		Classifica found = this.findById(classificaId);
		if (body.getEvento() != null)
		found.setEvento(eventoServ.findById(body.getEvento()));
		if (body.getPosizione01() != null)
		found.setPosizione01(userServ.findById(body.getPosizione01()));
		if (body.getPosizione02() != null)
		found.setPosizione02(userServ.findById(body.getPosizione02()));
		if (body.getPosizione03() != null)
		found.setPosizione03(userServ.findById(body.getPosizione03()));
		if (body.getPosizione04() != null)
		found.setPosizione04(userServ.findById(body.getPosizione04()));
		if (body.getPosizione05() != null)
		found.setPosizione05(userServ.findById(body.getPosizione05()));
		if (body.getPosizione06() != null)
		found.setPosizione06(userServ.findById(body.getPosizione06()));
		if (body.getPosizione07() != null)
		found.setPosizione07(userServ.findById(body.getPosizione07()));
		if (body.getPosizione08() != null)
		found.setPosizione08(userServ.findById(body.getPosizione08()));
		if (body.getPosizione09() != null)
		found.setPosizione09(userServ.findById(body.getPosizione09()));
		if (body.getPosizione10() != null)
		found.setPosizione10(userServ.findById(body.getPosizione10()));
		found.setPuntiPosizione01(150);
		found.setPuntiPosizione02(125);
		found.setPuntiPosizione03(100);
		found.setPuntiPosizione04(75);
		found.setPuntiPosizione05(50);
		found.setPuntiPosizione06(25);
		found.setPuntiPosizione07(25);
		found.setPuntiPosizione08(25);
		found.setPuntiPosizione09(25);
		found.setPuntiPosizione10(25);
		return classificaRepo.save(found);
	}

	public void findByIdAndDelete(UUID classificaId) throws NotFoundException {
		Classifica found = this.findById(classificaId);
		classificaRepo.delete(found);
	}

	// Filtri avanzati
	// Filtra Classifica per Evento
	public Classifica findByEvento(UUID eventoId) throws NotFoundException {
		return classificaRepo.findClassificaByEventoId(eventoId).orElseThrow(() -> new NotFoundException(eventoId));
	}
	
	// Find classifiche in cui un utente è arrivato primo
	public Page<Classifica> findByPrimoPosto(UUID atletaId, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort)); // (numero di pagina, numero di elementi per
																		// pagina, nome del campo per cui sortare)
		return classificaRepo.findByPosizione01(userServ.findById(atletaId), pageable);
	}

	// Nuomero di volte che un atleta è arrivato primo
	public int countPrimiPosti(UUID atletaId) {
		return classificaRepo.countPrimiPosti(userServ.findById(atletaId));
	}
}

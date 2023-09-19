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
import BoulderKing.entities.users.User;
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
		if (body.getEvento() != null)
			found.setEvento(eventoServ.findById(body.getEvento()));
		if (body.getPosizione01() != null) {
			User atleta01 = userServ.findById(body.getPosizione01());
			found.setPosizione01(atleta01);
			atleta01.setPrimoPosto(atleta01.getPrimoPosto() + 1);
			atleta01.setNumeroPodi(atleta01.getNumeroPodi() + 1);
			atleta01.setPuntiClassifica(atleta01.getPuntiClassifica() + found.getPuntiPosizione01());
		}

		if (body.getPosizione02() != null) {
			User atleta02 = userServ.findById(body.getPosizione02());
			found.setPosizione02(atleta02);
			atleta02.setNumeroPodi(atleta02.getNumeroPodi() + 1);
			atleta02.setPuntiClassifica(atleta02.getPuntiClassifica() + found.getPuntiPosizione02());
		}
		if (body.getPosizione03() != null) {
			User atleta03 = userServ.findById(body.getPosizione03());
			found.setPosizione03(atleta03);
			atleta03.setNumeroPodi(atleta03.getNumeroPodi() + 1);
			atleta03.setPuntiClassifica(atleta03.getPuntiClassifica() + found.getPuntiPosizione03());
		}
		if (body.getPosizione04() != null) {
			User atleta04 = userServ.findById(body.getPosizione04());
			found.setPosizione04(atleta04);
			atleta04.setPuntiClassifica(atleta04.getPuntiClassifica() + found.getPuntiPosizione04());
		}
		if (body.getPosizione05() != null) {
			User atleta05 = userServ.findById(body.getPosizione05());
			found.setPosizione05(atleta05);
			atleta05.setPuntiClassifica(atleta05.getPuntiClassifica() + found.getPuntiPosizione05());
		}
		if (body.getPosizione06() != null) {
			User atleta06 = userServ.findById(body.getPosizione06());
			found.setPosizione04(atleta06);
			atleta06.setPuntiClassifica(atleta06.getPuntiClassifica() + found.getPuntiPosizione06());
		}
		if (body.getPosizione07() != null) {
			User atleta07 = userServ.findById(body.getPosizione07());
			found.setPosizione07(atleta07);
			atleta07.setPuntiClassifica(atleta07.getPuntiClassifica() + found.getPuntiPosizione07());
		}
		if (body.getPosizione08() != null) {
			User atleta08 = userServ.findById(body.getPosizione08());
			found.setPosizione04(atleta08);
			atleta08.setPuntiClassifica(atleta08.getPuntiClassifica() + found.getPuntiPosizione08());
		}
		if (body.getPosizione09() != null) {
			User atleta09 = userServ.findById(body.getPosizione09());
			found.setPosizione09(atleta09);
			atleta09.setPuntiClassifica(atleta09.getPuntiClassifica() + found.getPuntiPosizione09());
		}
		if (body.getPosizione10() != null) {
			User atleta10 = userServ.findById(body.getPosizione10());
			found.setPosizione10(atleta10);
			atleta10.setPuntiClassifica(atleta10.getPuntiClassifica() + found.getPuntiPosizione10());
		}

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

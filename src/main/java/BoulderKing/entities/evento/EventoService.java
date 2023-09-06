package BoulderKing.entities.evento;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.entities.evento.eventopayload.NewEventoPayload;
import BoulderKing.entities.users.UsersService;
import BoulderKing.exceptions.NotFoundException;

@Service
public class EventoService {
	private final EventoRepository eventoRepo;

	@Autowired
	public EventoService(EventoRepository eventoRepo) {
		this.eventoRepo = eventoRepo;
	}

	@Autowired
	public UsersService userServ;

	public Evento create(NewEventoPayload body) {
		Evento newEvento = new Evento();
		newEvento.setNomeEvento(body.getNomeEvento());
		newEvento.setLocalità(body.getLocalità());
		newEvento.setPuntiEvento(body.getPuntiEvento());
		newEvento.setData(body.getData());
		newEvento.setOrganizzatore(userServ.findById(body.getOrganizzatore()));
		return eventoRepo.save(newEvento);
	}

	public Page<Evento> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort)); // (numero di pagina, numero di elementi per
																		// pagina, nome del campo per cui sortare)
		return eventoRepo.findAll(pageable);
	}

	public Evento findById(UUID eventoId) throws NotFoundException {
		return eventoRepo.findById(eventoId).orElseThrow(() -> new NotFoundException(eventoId));
	}

	public Evento findByIdAndUpdate(UUID eventoId, NewEventoPayload body) throws NotFoundException {
		Evento found = this.findById(eventoId);
		found.setNomeEvento(body.getNomeEvento());
		found.setLocalità(body.getLocalità());
		found.setPuntiEvento(body.getPuntiEvento());
		found.setData(body.getData());
		found.setOrganizzatore(userServ.findById(body.getOrganizzatore()));
		return eventoRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Evento found = this.findById(id);
		eventoRepo.delete(found);
	}
}

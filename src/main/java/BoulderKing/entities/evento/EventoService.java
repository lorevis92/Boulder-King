package BoulderKing.entities.evento;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BoulderKing.entities.evento.eventopayload.NewEventoPayload;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersRepository;
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

	@Autowired
	public UsersRepository userRepo;

	public Evento create(NewEventoPayload body) {
		Evento newEvento = new Evento();
		newEvento.setNomeEvento(body.getNomeEvento());
		newEvento.setLocalità(body.getLocalità());
		newEvento.setPuntiEvento(body.getPuntiEvento());
		newEvento.setData(body.getData());
		newEvento.setImmagineEvento(body.getImmagineEvento());
		newEvento.setOrganizzatore(userServ.findById(body.getOrganizzatore()));
		newEvento.setCitta(body.getCitta());
		newEvento.setProvincia(body.getProvincia());
		newEvento.setRegione(body.getRegione());
		newEvento.hasPassed();
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

	// Eventi organizzati da un certo ente
	public Page<Evento> findByOrganizzatore(UUID organizzatoreId, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort)); // (numero di pagina, numero di elementi per
																		// pagina, nome del campo per cui sortare)
		return eventoRepo.findByOrganizzatoreId(organizzatoreId, pageable);
	}

	// Eventi filtrati per nome evento
	public Page<Evento> findByNome(String nomeEvento, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort)); // (numero di pagina, numero di elementi per
																		// pagina, nome del campo per cui sortare)
		return eventoRepo.findByNomeEventoContainingIgnoreCase(nomeEvento, pageable);
	}
	
	// Aggiungi partecipante ad una gara

    public Evento aggiungiPartecipante(UUID idEvento, UUID idUtente) {
        // Recupera l'evento dal repository
        Evento evento = eventoRepo.findById(idEvento)
            .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + idEvento));

        // Recupera l'utente dal repository tramite l'ID
        User utente = userServ.findById(idUtente);

        // Verifica se l'utente esiste
        if (utente == null) {
            throw new NotFoundException("Utente non trovato con ID: " + idUtente);
        }

        // Chiama il metodo aggiungiPartecipante con l'oggetto User
        evento.aggiungiPartecipante(utente);

        // Salva l'evento aggiornato nel repository
        return eventoRepo.save(evento);
    }

	public Page<User> getPartecipantiPaginated(UUID idEvento, Pageable pageable) {
		Evento evento = eventoRepo.findById(idEvento)
				.orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + idEvento));

		List<User> partecipanti = evento.getPartecipanti();
		// Ordina i partecipanti in base alla posizione in classifica
		partecipanti.sort(Comparator.comparingInt(User::getPosizioneClassifica));

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), partecipanti.size());

		List<User> partecipantiPaginati = partecipanti.subList(start, end);

		return new PageImpl<>(partecipantiPaginati, pageable, partecipanti.size());
	}



//	public Page<User> getPartecipantiPaginated(UUID idEvento, Pageable pageable) {
//		Evento evento = eventoRepo.findById(idEvento)
//				.orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + idEvento));
//
//		List<User> partecipanti = evento.getPartecipanti();
//		// Ordina i partecipanti in base alla posizione in classifica
//		partecipanti.sort(Comparator.comparingInt(User::getPosizioneClassifica));
//
//		int start = (int) pageable.getOffset();
//		int end = (start + pageable.getPageSize()) > partecipanti.size() ? partecipanti.size()
//				: (start + pageable.getPageSize());
//
//		List<User> partecipantiPaginati = partecipanti.subList(start, end);
//
//		return new PageImpl<>(partecipantiPaginati, pageable, partecipanti.size());
//	}

// TROVA UN EVENTO CONOSCENDO UNA CLASSIFICA
	public Evento getEventoByClassificaId(UUID classificaId) throws NotFoundException {
		return eventoRepo.findByClassificaId(classificaId)
				.orElseThrow(() -> new NotFoundException("Evento non trovato per l'ID classifica: " + classificaId));
	}

	// Filtraggio
	public Page<Evento> findByFilters(String nomeEvento, String nomeEnte, String regione, String provincia,
			String citta,
			String isPassed, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return eventoRepo.findByFilters(nomeEvento, nomeEnte, regione, provincia, citta, isPassed, pageable);
	}
}



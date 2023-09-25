package BoulderKing.entities.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.TipoUser;
import BoulderKing.Enum.ZonaItalia;
import BoulderKing.entities.evento.Evento;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);

	Page<User> findByTipoUser(TipoUser tipoUser, Pageable pageable);

	Page<User> findByPuntiClassificaGreaterThan(int minPuntiClassifica, Pageable pageable);

	Page<User> findByPosizioneClassificaLessThan(int minPosizioneClassifica, Pageable pageable);

	Optional<User> findByPosizioneClassifica(int posizioneClassifica);

	Page<User> findByTipoEnte(TipoEnte tipoEnte, Pageable pageable);

	// Restituisci user in maniera ordinata
	Page<User> findAllByOrderByPosizioneClassifica(Pageable pageable);

	Page<User> findAllByOrderByNomeEnte(Pageable pageable);

	// Trova User grazie parte del nome
	Page<User> findByNomeEnteContainingIgnoreCase(String nomeEnte, Pageable pageable);

	Page<User> findByNameContainingIgnoreCase(String parteNome, Pageable pageable);

	Page<User> findBySurnameContainingIgnoreCase(String parteNome, Pageable pageable);

	// Page<User> findByUserNameContainingIgnoreCase(String parteNome, Pageable
	// pageable);

	// Metodo per ottenere gli utenti partecipanti a un evento paginati e ordinati
	Page<User> findAllByListaEventiContains(Evento evento, Pageable pageable);

	// Metodo per il filtraggio

	@Query("SELECT u FROM User u WHERE "
			+ "(:nomeEnte IS NULL OR LOWER(u.nomeEnte) LIKE LOWER(CONCAT('%', :nomeEnte, '%'))) "
			+ "AND (:regione IS NULL OR LOWER(u.regione) = LOWER(:regione)) "
			+ "AND (:provincia IS NULL OR LOWER(u.provincia) = LOWER(:provincia)) "
			+ "AND (:citta IS NULL OR LOWER(u.citta) = LOWER(:citta)) "
			+ "AND (:zonaItalia IS NULL OR u.zonaItalia = :zonaItalia) "
			+ "AND (:tipoEnte IS NULL OR u.tipoEnte = :tipoEnte)")
	Page<User> findByFilters(String nomeEnte, String regione, String provincia, String citta, ZonaItalia zonaItalia,
			TipoEnte tipoEnte, Pageable pageable);

}
package BoulderKing.entities.evento;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {
	Page<Evento> findByNomeEventoContainingIgnoreCase(String nomeEvento, Pageable pageable);

	Page<Evento> findByOrganizzatoreId(UUID enteId, Pageable pageable);

	Optional<Evento> findByClassificaId(UUID classificaId);

	@Query("SELECT e FROM Evento e JOIN e.organizzatore u WHERE "
			+ "(:nomeEvento IS NULL OR LOWER(e.nomeEvento) LIKE LOWER(CONCAT('%', :nomeEvento, '%'))) "
			+ "AND (:nomeEnte IS NULL OR LOWER(u.nomeEnte) LIKE LOWER(CONCAT('%', :nomeEnte, '%'))) "
			+ "AND (:regione IS NULL OR LOWER(e.regione) LIKE LOWER(CONCAT(:regione, '%'))) "
			+ "AND (:provincia IS NULL OR LOWER(e.provincia) LIKE LOWER(CONCAT(:provincia, '%'))) "
			+ "AND (:citta IS NULL OR LOWER(e.citta) LIKE LOWER(CONCAT(:citta, '%'))) "
			+ "AND (:zonaItalia IS NULL OR UPPER(e.zonaItalia) LIKE UPPER(CONCAT(:zonaItalia, '%'))) "
			+ "AND (:isPassed IS NULL OR UPPER(e.isPassed) LIKE UPPER(CONCAT(:isPassed, '%')))")
	Page<Evento> findByFilters(String nomeEvento, String nomeEnte, String zonaItalia, String regione, String provincia,
			String citta,
			String isPassed, Pageable pageable);

}

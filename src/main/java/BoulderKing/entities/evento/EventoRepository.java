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
			+ "AND (:regione IS NULL OR LOWER(e.regione) = LOWER(:regione)) "
			+ "AND (:provincia IS NULL OR LOWER(e.provincia) = LOWER(:provincia)) "
			+ "AND (:citta IS NULL OR LOWER(e.citta) = LOWER(:citta)) "
			+ "AND (:isPassed IS NULL OR e.isPassed = :isPassed)")
	Page<Evento> findByFilters(String nomeEvento, String nomeEnte, String regione, String provincia, String citta,
			String isPassed, Pageable pageable);

}

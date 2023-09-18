package BoulderKing.entities.evento;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {
	Page<Evento> findByNomeEventoContainingIgnoreCase(String nomeEvento, Pageable pageable);

	Page<Evento> findByOrganizzatoreId(UUID enteId, Pageable pageable);

	Optional<Evento> findByClassificaId(UUID classificaId);

	}

package BoulderKing.entities.classifica;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import BoulderKing.entities.users.User;

@Repository
public interface ClassificaRepository extends JpaRepository<Classifica, UUID> {
	Optional<Classifica> findClassificaByEventoId(UUID eventoId);

	Page<Classifica> findByPosizione01(User atleta, Pageable pageable);

	@Query("SELECT COUNT(*) FROM Classifica c WHERE c.posizione01 = :atletaId")
	int countPrimiPosti(@Param("atletaId") User atleta);
}

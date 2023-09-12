package BoulderKing.entities.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.TipoUser;

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


}
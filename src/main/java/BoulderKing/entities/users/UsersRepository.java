package BoulderKing.entities.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BoulderKing.Enum.TipoUser;
import BoulderKing.entities.atleta.Atleta;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
	public Atleta save(Atleta atleta);

	Optional<User> findByEmail(String email);

	Page<User> findByTipoUser(TipoUser tipoUser, Pageable pageable);
}
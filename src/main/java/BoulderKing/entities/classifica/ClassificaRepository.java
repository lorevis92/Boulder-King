package BoulderKing.entities.classifica;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificaRepository extends JpaRepository<Classifica, UUID> {

}

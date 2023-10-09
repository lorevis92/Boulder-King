package BoulderKing.entities.caricaImmagini;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, UUID> {
	Optional<Image> findByName(String name);
//
//	Optional<Image> findById(UUID id);
//
//	Optional<Image> deleteById(UUID id);
}

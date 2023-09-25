package BoulderKing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.ZonaItalia;
import BoulderKing.entities.users.User;
import BoulderKing.entities.users.UsersRepository;
import BoulderKing.entities.users.UsersService;
@SpringJUnitConfig
@SpringBootTest
class BoulderKingApplicationTests {

	@Autowired
	private UsersService usersService;

	@MockBean
	private UsersRepository usersRepository;

	@Test
	public void testFindById() {
		// Prepariamo un utente fittizio da restituire quando viene chiamato il
		// repository
		UUID userId = UUID.randomUUID();
		User user = new User();
		user.setId(userId);
		user.setEmail("test@example.com");

		// Configuriamo il comportamento del mock del repository
		when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

		// Eseguiamo il metodo da testare
		User foundUser = usersService.findById(userId);

		// Verifichiamo che il metodo restituisca l'utente corretto
		assertNotNull(foundUser);
		assertEquals(userId, foundUser.getId());
		assertEquals("test@example.com", foundUser.getEmail());

		// Verifichiamo che il metodo abbia chiamato il repository.findById() una volta
		verify(usersRepository, times(1)).findById(userId);
	}

	@Test
	public void testFindByFilters() {
		// Creare dati di prova nel database
		User user1 = new User("nomeEnte1", "regione1", "provincia1", "citta1", ZonaItalia.NORD, TipoEnte.PALESTRA);
		User user2 = new User("nomeEnte2", "regione2", "provincia2", "citta2", ZonaItalia.CENTRO, TipoEnte.FALESIA);
		usersRepository.save(user1);
		usersRepository.save(user2);

		// Eseguire la query con i parametri noti
		Page<User> result = usersRepository
				.findByNomeEnteContainingIgnoreCaseAndRegioneAndProvinciaAndCittaAndZonaItaliaAndTipoEnte("nomeEnte1",
						"regione1", "provincia1", "citta1", ZonaItalia.NORD, TipoEnte.PALESTRA,
						PageRequest.of(0, 10, Sort.by("id")));

		// Verificare che i risultati contengano ciò che ti aspetti
		assertEquals(1, result.getTotalElements());
		assertEquals("nomeEnte1", result.getContent().get(0).getNomeEnte());
	}


}

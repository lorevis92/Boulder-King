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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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




}

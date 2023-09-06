package BoulderKing;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import BoulderKing.entities.users.UsersRepository;
import BoulderKing.entities.users.UsersService;
import BoulderKing.entities.users.payloads.UserRequestPayload;

@Component
public class UsersRunner implements CommandLineRunner {
	@Autowired
	UsersService usersServ;

	@Autowired
	UsersRepository usersRepo;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));

		for (int i = 0; i < 30; i++) {
			String name = faker.name().firstName();
			String surname = faker.name().lastName();
			String email = faker.internet().emailAddress();
			String password = "1234";
			// String creditCard = faker.business().creditCardNumber();
			UserRequestPayload user = new UserRequestPayload(email, password);
//			usersServ.create(user);
		}
//		Atleta nuovoAtleta = new Atleta();
//		nuovoAtleta.setName("Nome Atleta");
//		nuovoAtleta.setSurname("Cognome Atleta");
//		nuovoAtleta.setUserName("Username");
//		nuovoAtleta.setEmail("eyeyeyd@example.com");
//		nuovoAtleta.setPassword("Password");
//		nuovoAtleta.setTipoUser(TipoUser.ATLETA);
//		usersRepo.save(nuovoAtleta);

	}

}
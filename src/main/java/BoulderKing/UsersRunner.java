package BoulderKing;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import BoulderKing.entities.users.UsersRepository;
import BoulderKing.entities.users.UsersService;
import BoulderKing.googleapi.GoogleMapsService;

@Component
public class UsersRunner implements CommandLineRunner {
	@Autowired
	UsersService usersServ;

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	GoogleMapsService mapServ;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));
 
//		for (int i = 0; i < 15; i++) {
//			String name = faker.name().firstName();
//			String surname = faker.name().lastName();
//			String email = faker.internet().emailAddress();
//			String password = "1234";

			// String creditCard = faker.business().creditCardNumber();
//			UserRequestPayload user = new UserRequestPayload(email, password);
//			usersServ.create(user);
//		}
// CREAZIONE ENTI PER RIEMPIRE IL DATABASE
//		for (int i = 0; i < 15; i++) {
//			String email = faker.internet().emailAddress();
//			String password = "1234";
//			String nomeEnte = faker.beer().name();
//			String numeroTelefonico = faker.phoneNumber().cellPhone();
//			String indirizzo = faker.address().fullAddress();
//			String info = faker.beer().style();
//			User user = new User(email, password, nomeEnte, numeroTelefonico, indirizzo, info);
//			usersRepo.save(user);
//		}
// CREAZIONE ATLETI PER RIEMPIRE IL DATABASE
//		for (int i = 0; i < 15; i++) {
//			String email = faker.internet().emailAddress();
//			String password = "1234";
//			String userName = faker.name().username();
//			Integer posizioneClassifica = i + 1;
//			String name = faker.name().firstName();
//			String surname = faker.name().lastName();
//
//			User user = new User(email, password, name, surname, userName, posizioneClassifica);
////			usersRepo.save(user);
//		}
		
		this.mapServ.fetchClimbingGymsFromGoogleMaps();

	}

}
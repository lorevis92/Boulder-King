package BoulderKing;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import BoulderKing.users.UsersService;
import BoulderKing.users.payloads.UserRequestPayload;

@Component
public class UsersRunner implements CommandLineRunner {
	@Autowired
	UsersService usersServ;

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

	}

}
package BoulderKing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import BoulderKing.exceptions.UnauthorizedException;
import BoulderKing.users.User;
import BoulderKing.users.UsersService;
import BoulderKing.users.payloads.LoginSuccessfullPayload;
import BoulderKing.users.payloads.UserLoginPayload;
import BoulderKing.users.payloads.UserRequestPayload;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UsersService usersService;

	@Autowired
	JWTTools jwtTools;

	@Autowired
	PasswordEncoder bcrypt;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody @Validated UserRequestPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		User created = usersService.create(body);
		return created;
	}

	@PostMapping("/login")
	public LoginSuccessfullPayload login(@RequestBody UserLoginPayload body) {

		User user = usersService.findByEmail(body.getEmail());
		if (bcrypt.matches(body.getPassword(), user.getPassword())) {
			String token = jwtTools.createToken(user);
			return new LoginSuccessfullPayload(token);
		} else {
			throw new UnauthorizedException("Credenziali non valide!");
		}
	}

}
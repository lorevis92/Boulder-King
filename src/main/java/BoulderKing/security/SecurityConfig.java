package BoulderKing.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	JWTAuthFilter jwtFilter;
	@Autowired
	CorsFilter corsFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// http.cors(c -> c.disable());



		http.csrf(c -> c.disable());

		// Se vogliamo utilizzare JWT dobbiamo disabilitare anche le sessioni
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/users/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/atleti/**").permitAll());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/enti/**").permitAll());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/eventi/**").permitAll());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/classifiche/**").permitAll());

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(corsFilter, JWTAuthFilter.class);

		return http.build();
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11); // 11 è il cosiddetto numero di ROUNDS ovvero quante volte viene eseguito
												// l'algoritmo. In pratica ci serve per settare la velocità di
												// esecuzione dell'algoritmo (+ è alto il numero, + lento l'algoritmo)
	}

}

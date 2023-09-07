package BoulderKing.entities.users;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import BoulderKing.Enum.TipoEnte;
import BoulderKing.Enum.TipoUser;
import BoulderKing.entities.evento.Evento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {
	@Id
	@GeneratedValue
	private UUID id;
	@Column(nullable = false, unique = true)
	private String email;
//	private Immagine profilo;
//	private Immagine copertina;
	private String userName;
	private String name;
	private String surname;
	private int posizioneClassifica;
	private int puntiClassifica;
	@ManyToMany(mappedBy = "partecipanti")
	@JsonManagedReference
	private List<Evento> listaEventi;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Enumerated(EnumType.STRING)
	private TipoUser tipoUser;
	private LocalDate dataRegistrazione;
	String nomeEnte;
	String numeroTelefonico;
	String emailContatto;
	String indirizzo;
	String informazioni;
	@Enumerated(EnumType.STRING)
	TipoEnte tipoEnte;
	@OneToMany(mappedBy = "organizzatore")
	private List<Evento> listaEventiOrganizzati;



	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.role = Role.USER;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
}
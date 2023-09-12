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
	@Column(nullable = true)
	private Integer posizioneClassifica;
	@Column(nullable = true)
	private Integer puntiClassifica;
	@Column(nullable = true)
	private Integer primoPosto;
	@Column(nullable = true)
	private Integer numeroPodi;
	@Column(nullable = true)
	private Integer numeroPartecipazioni;
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

	// CREAZIONE ENTI
	public User(String email, String password, String nomeEnte, String telefono, String indirizzo, String info) {
		this.email = email;
		this.password = password;
		this.role = Role.USER;
		this.tipoUser = TipoUser.ENTE;
		this.nomeEnte = nomeEnte;
		this.numeroTelefonico = telefono;
		this.indirizzo = indirizzo;
		this.informazioni = info;
	}

	// CREAZIONE ATLETI
	public User(String email, String password, String name, String surname, String userName,
			Integer posizioneClassifica) {
		this.email = email;
		this.password = password;
		this.role = Role.USER;
		this.tipoUser = TipoUser.ATLETA;
		this.userName = userName;
		this.posizioneClassifica = posizioneClassifica;
		this.numeroPartecipazioni = 0;
		this.numeroPodi = 0;
		this.puntiClassifica = 0;
		this.primoPosto = 0;
		this.name = name;
		this.surname = surname;
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
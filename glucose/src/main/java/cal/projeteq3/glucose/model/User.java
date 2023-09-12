package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userID;

	@Embedded
	private Credentials credentials;

	@Embedded
	private Person person;

	public User(String firstName, String lastName, String email, String password){
		this.person = new Person(firstName, lastName, LocalDate.now());
		this.credentials = new Credentials(email, password, Role.STUDENT);
	}

	public String getFirstName(){
		return person.getFirstName();
	}

	public String getLastName(){
		return person.getLastName();
	}

	public LocalDate getBirthDate(){
		return person.getBirthDate();
	}

	public String getEmail(){
		return credentials.getEmail();
	}

	public String getPassword(){
		return credentials.getPassword();
	}

	public Role getRole(){
		return credentials.getRole();
	}

	public void setFirstName(String firstName){
		person.setFirstName(firstName);
	}

	public void setLastName(String lastName){
		person.setLastName(lastName);
	}

	public void setEmail(String email){
		credentials.setEmail(email);
	}

	public void setPassword(String password){
		credentials.setPassword(password);
	}

	public void setRole(Role role){
		credentials.setRole(role);
	}

	public void setBirthDate(LocalDate birthDate){
		person.setBirthDate(birthDate);
	}

}



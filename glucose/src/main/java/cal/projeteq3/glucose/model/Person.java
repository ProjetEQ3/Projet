package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.validation.Validation;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Embeddable
public final class Person{
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "birth_date")
	private LocalDate birthDate;

	public Person(){}

	public Person(String firstName, String lastName, LocalDate birthDate){
		setFirstName(firstName);
		setLastName(lastName);
		setBirthDate(birthDate);
	}

	public void setFirstName(String firstName){
		Validation.validateName(firstName);
		this.firstName = firstName;
	}

	public void setLastName(String lastName){
		Validation.validateName(lastName);
		this.lastName = lastName;
	}

	public void setBirthDate(LocalDate birthDate){
		Validation.validateBirthDate(birthDate);
		this.birthDate = birthDate;
	}

	public void copy(Person person){
		setFirstName(person.getFirstName());
		setLastName(person.getLastName());
		setBirthDate(person.getBirthDate());
	}

}

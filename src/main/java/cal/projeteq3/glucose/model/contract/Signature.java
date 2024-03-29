package cal.projeteq3.glucose.model.contract;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Signature{
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private LocalDate signatureDate = LocalDate.now();

	@JoinColumn
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Contract contract;

	@Override
	public String toString() {
		return firstName + " " + lastName + " (" + signatureDate + ")";
	}
}

package cal.projeteq3.glucose.model.evaluation;

import jakarta.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Evaluation{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String studentName;
	private String studentProgram;
	private String companyName;
	private String supervisorName;
	private String supervisorPosition;
	private String supervisorPhoneNumber;
	private String supervisorAddress;
	private String supervisorAddressVille;
	private String supervisorAddressPostalCode;
	private String supervisorFax;
	private String internshipTitle;
	private String internshipStartDate;
	private String internshipNumber;
}

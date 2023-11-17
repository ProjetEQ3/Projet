package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.exception.unauthorizedException.SignaturePrerequisiteNotMetException;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contract{
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Employer employer;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Student student;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private JobOffer jobOffer;

	@OneToOne
	private Signature employerSignature;

	@OneToOne
	private Signature studentSignature;

	@OneToOne
	private Signature managerSignature;

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime creationDate = LocalDateTime.now();

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastModificationDate = LocalDateTime.now();

	private boolean isComplete = false;

	public Contract(Employer employer, Student student, JobOffer jobOffer){
		this.employer = employer;
		this.student = student;
		this.jobOffer = jobOffer;
	}

	public void setEmployerSignature(Signature employerSignature){
		if(this.employerSignature != null) throw new IllegalStateException("Employer signature already set");

		this.employerSignature = employerSignature;
		lastModificationDate = LocalDateTime.now();
	}

	public void setStudentSignature(Signature studentSignature){
		if(this.studentSignature != null) throw new IllegalStateException("Student signature already set");

		this.studentSignature = studentSignature;
		lastModificationDate = LocalDateTime.now();
	}

	public void setManagerSignature(Signature directorSignature){
		if(this.managerSignature != null) throw new IllegalStateException("Director signature already set");
		if(this.employerSignature == null || this.studentSignature == null) throw new SignaturePrerequisiteNotMetException();

		this.managerSignature = directorSignature;
		isComplete = true;
		lastModificationDate = LocalDateTime.now();
	}

	public String getStudentSignatureContract(){
		if(studentSignature != null) return studentSignature.toString();
		return "__________________________";
	}

	public String getEmployerSignatureContract(){
		if(employerSignature != null) return employerSignature.toString();
		return "__________________________";
	}

	public String getManagerSignatureContract(){
		if(managerSignature != null) return managerSignature.toString();
		return "__________________________";
	}

	public boolean isReadyToSign(){
		return employer != null && student != null && jobOffer != null;
	}

}

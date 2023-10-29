package cal.projeteq3.glucose.model.cvFile;

import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.validation.Validation;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
public final class CvFile{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false, updatable = false)
	private String fileName;

	@Column(nullable = false, updatable = false)
	private byte[] fileData;

	private CvState cvState = CvState.SUBMITTED;

	@OneToOne(mappedBy = "cvFile")
	@ToString.Exclude
	private Student student;

	private String refusReason;

	@Builder
	public CvFile(Long id, String fileName, byte[] fileData, CvState cvState, String refusReason){
		setId(id);
		setFileName(fileName);
		setFileData(fileData);
		setCvState(cvState);
		setRefusReason(refusReason);
	}

	public void setFileName(String fileName){
		Validation.validateCvFileName(fileName);
		this.fileName = fileName;
	}

	public void setCvState(CvState cvState){
		if(cvState == null) this.cvState = CvState.SUBMITTED;
		else this.cvState = cvState;
	}

}

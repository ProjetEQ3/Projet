package cal.projeteq3.glucose.model.cvFile;

import cal.projeteq3.glucose.model.user.Student;
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

	private CvState cvState;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Student student;

	@Builder
	public CvFile(Long id, String fileName, byte[] fileData, CvState cvState, Student student) {
		this.id = id;
		this.fileName = fileName;
		this.fileData = fileData;

		if (cvState == null) this.cvState = CvState.SUBMITTED;
		else this.cvState = cvState;

		this.student = student;
	}

}

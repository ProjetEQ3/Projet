package cal.projeteq3.glucose.model.cvFile;

import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cvFile")
@Table(name = "cv_file")
public final class CvFile{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "file_name", unique = true, nullable = false, updatable = false)
	private String fileName;

	@Column(name = "file_data", nullable = false, updatable = false)
	private byte[] fileData;

	@Column(name = "cv_state")
	private CvState cvState;

	@OneToOne(mappedBy = "cv", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Student student;

	/*public CvFile(String fileName, byte[] fileData, Student student){
		this.fileName = fileName;
		this.fileData = fileData;
		this.state = State.SUBMITTED; //ca va pas marche car il va met state to submitted quand il prend le cv de la base de donnee
		this.student = student; // doit pas prendre student ici
	}*/

	public CvFile(String fileName, byte[] fileData, CvState cvState){
		this.fileName = fileName;
		this.fileData = fileData;
		this.cvState = cvState;
	}

}

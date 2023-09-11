package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
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

	@OneToOne(mappedBy = "cvFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Student student;

}

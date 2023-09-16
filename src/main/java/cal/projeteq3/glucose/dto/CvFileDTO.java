package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.model.CvState;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CvFileDTO{
	private Long id;
	private String fileName;
	private byte[] fileData;
	private CvState cvState;

	public CvFileDTO(CvFile cvFile){
		id = cvFile.getId();
		fileName = cvFile.getFileName();
		fileData = cvFile.getFileData();
		cvState = cvFile.getCvState();
	}

	public CvFile toEntity(){
		return CvFile.builder().fileName(fileName).fileData(fileData).cvState(cvState).build();
	}

}

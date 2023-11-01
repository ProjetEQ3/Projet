package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvFileDTO{
	private Long id;
	private String fileName;
	private byte[] fileData;
	private CvState cvState;
	private String refusReason;

	public CvFileDTO(CvFile cvFile){
		id = cvFile.getId();
		fileName = cvFile.getFileName();
		fileData = cvFile.getFileData();
		cvState = cvFile.getCvState();
		refusReason = cvFile.getRefusReason();
	}

	public CvFile toEntity(){
		return CvFile.builder().fileName(fileName).fileData(fileData).cvState(cvState).refusReason(refusReason).build();
	}

}

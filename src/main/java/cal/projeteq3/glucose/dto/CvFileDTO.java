package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.model.State;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CvFileDTO{
	private Long id;
	private String fileName;
	private byte[] fileData;
	private State state;

	public CvFileDTO(CvFile cvFile){
		id = cvFile.getId();
		fileName = cvFile.getFileName();
		fileData = cvFile.getFileData();
		state = cvFile.getState();
	}

	public CvFile toEntity(){
		return CvFile.builder().fileName(fileName).fileData(fileData).state(state).build();
	}

}

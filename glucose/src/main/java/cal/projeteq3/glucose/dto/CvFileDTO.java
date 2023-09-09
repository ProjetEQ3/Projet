package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.CvFile;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CvFileDTO{
	private Long id;
	private String fileName;
	private byte[] fileData;

	public CvFileDTO(CvFile cvFile){
		fileName = cvFile.getFileName();
		fileData = cvFile.getFileData();
	}

	public CvFile toEntity(){
		return CvFile.builder().fileName(fileName).fileData(fileData).build();
	}

}

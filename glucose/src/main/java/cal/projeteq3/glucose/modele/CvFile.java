package cal.projeteq3.glucose.domain;

import cal.projeteq3.glucose.validation.Validation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "cvFile")
@Table(name = "cv_file")
@Getter
public final class CvFile extends Model{
	private final static String SERIAL_PREFIX = "cvFile";

	@Column(name = "file_name", unique = true, nullable = false, updatable = false)
	private String fileName;

	@Column(name = "file_data", nullable = false, updatable = false)
	private byte[] fileData;

	public CvFile(){}

	@Builder
	public CvFile(String fileName, byte[] fileData){
		setFileName(fileName);
		setFileData(fileData);
	}

	@Override
	public String getSerialPrefix(){
		return SERIAL_PREFIX;
	}

	public void setFileName(String fileName){
		Validation.validateCvFileName(fileName);
		this.fileName = fileName;
	}

	public void setFileData(byte[] fileData){
		//TODO: add org.apache.tika.Tika; pour valider le type de fichier
		this.fileData = fileData;
	}

}


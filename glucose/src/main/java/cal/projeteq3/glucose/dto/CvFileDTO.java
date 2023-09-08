package cal.projeteq3.glucose.domain.dto;

import cal.projeteq3.glucose.domain.CvFile;
import cal.projeteq3.glucose.exception.badRequestException.ValidationException;
import cal.projeteq3.glucose.validation.Validation;

public class CvFileDto extends ModelDto{
	private String fileName;
	private byte[] fileData;

	public CvFileDto(){
		super();
		fileName = null;
		fileData = null;
	}

	public CvFileDto(String serial, String fileName, byte[] fileData){
		super(serial);
		this.fileName = fileName;
		this.fileData = fileData;
		validate();
	}

	public CvFileDto(CvFile cvFile){
		super(cvFile);
		fileName = cvFile.getFileName();
		fileData = cvFile.getFileData();
	}

	public CvFile toEntity(){
		return CvFile.builder()
			.fileName(fileName)
			.fileData(fileData)
			.build();
	}

	@Override
	public void validate(){
		clearErrors();
		super.validate();
		try{
			Validation.validateCvFileName(fileName);
			//TODO: Validation.validateCvFileType(file);
		}catch(ValidationException e){
			addError(e.getMessage());
		}
	}

}

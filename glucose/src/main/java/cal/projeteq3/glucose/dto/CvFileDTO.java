package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.exception.badRequestException.ValidationException;
import cal.projeteq3.glucose.validation.Validation;

public class CvFileDTO extends ModelDto{
	private String fileName;
	private byte[] fileData;

	public CvFileDTO(){
		super();
		fileName = null;
		fileData = null;
	}

	public CvFileDTO(String serial, String fileName, byte[] fileData){
		super(serial);
		this.fileName = fileName;
		this.fileData = fileData;
		validate();
	}

	public CvFileDTO(CvFile cvFile){
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

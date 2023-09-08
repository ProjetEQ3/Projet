package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.dto.CvFileDto;
import cal.projeteq3.glucose.exception.badRequestException.CvFileExistException;
import cal.projeteq3.glucose.exception.badRequestException.CvFileNotFoundException;
import cal.projeteq3.glucose.repository.CvFileRepository;

public class CvFileService{
	private final CvFileRepository cvFileRepository;

	public CvFileService(CvFileRepository cvFileRepository){
		this.cvFileRepository = cvFileRepository;
	}

	public CvFileDto create(CvFileDto cvFileDto){
		if(cvFileRepository.existsBySerial(cvFileDto.getSerial())) throw new CvFileExistException();
		CvFile cvFile = cvFileDto.toEntity();
		cvFileRepository.save(cvFile);
		return new CvFileDto(cvFile);
	}

	public CvFileDto read(String serial){
		CvFile cvFile = cvFileRepository.findBySerial(serial).orElseThrow(CvFileNotFoundException::new);
		if(cvFile == null) throw new CvFileNotFoundException();
		return new CvFileDto(cvFile);
	}

}

package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.exception.request.CvFileNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOfferNotFoundException;
import cal.projeteq3.glucose.exception.request.ManagerNotFoundException;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.repository.CvFileRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService{

	private final ManagerRepository managerRepository;
	private final StudentRepository studentRepository;
	private final JobOfferRepository jobOfferRepository;
	private final CvFileRepository cvRepository;

	@Autowired
	public ManagerService(
		ManagerRepository managerRepository, StudentRepository studentRepository,
		JobOfferRepository jobOfferRepository, CvFileRepository cvRepository
	){
		this.managerRepository = managerRepository;
		this.studentRepository = studentRepository;
		this.jobOfferRepository = jobOfferRepository;
		this.cvRepository = cvRepository;
	}

	// database operations here

    public ManagerDTO createManager(ManagerDTO managerDTO) {
        return new ManagerDTO(managerRepository.save(managerDTO.toEntity()));
    }

    public List<ManagerDTO> getAllManagers() {
		List<Manager> managers = managerRepository.findAll();
        return managers.stream().map(ManagerDTO::new).collect(Collectors.toList());
    }

    public ManagerDTO getManagerByID(Long id) {
		return new ManagerDTO(managerRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id)));
    }

    public ManagerDTO updateManager(Long id, ManagerDTO updatedManager) {
        Manager manager = managerRepository.findById(id)
				.orElseThrow(() -> new ManagerNotFoundException(id));

		manager.setFirstName(updatedManager.getFirstName());
		manager.setLastName(updatedManager.getLastName());
		manager.setEmail(updatedManager.getEmail());

		return new ManagerDTO(managerRepository.save(manager));
    }

	public void deleteManager(Long id){
		managerRepository.deleteById(id);
	}

//    CV File
	public CvFileDTO getCvById(Long id){
		return new CvFileDTO(cvRepository.findById(id).orElseThrow());
	}

	public List<CvFileDTO> getAllCv(){
		return cvRepository.findAll().stream().map(CvFileDTO::new).toList();
	}

	public List<CvFileDTO> getSubmittedCv(){
		return cvRepository.findAllByCvState(CvState.SUBMITTED).stream().map(CvFileDTO::new).toList();
	}

	public List<CvFileDTO> getAllCvFileByStudent(Long id){
		return cvRepository.findAllByStudent(studentRepository.findById(id).orElseThrow()).stream().map(CvFileDTO::new).toList();
	}

	public List<CvFileDTO> getAllCvFileByStudentMatricule(String matricule){
		return cvRepository.findAllByStudent(studentRepository.findByMatricule(matricule)).stream().map(CvFileDTO::new).toList();
	}

	public CvFileDTO getCvFileByStudentAndFileName(String matricule, String fileName){
		return new CvFileDTO(cvRepository.findByStudentAndFileName(studentRepository.findByMatricule(matricule), fileName));
	}

	public void deleteCvFile(Long id){
		cvRepository.deleteById(id);
	}

	public void deleteAllCvFileByStudentMatricule(String matricule){
		cvRepository.deleteAllByStudent(studentRepository.findByMatricule(matricule));
	}

	public CvFileDTO rejectCv(Long id, String reason) {
		CvFile cvFile = cvRepository.findById(id)
				.orElseThrow(CvFileNotFoundException::new);
		cvFile.setCvState(CvState.REFUSED);
		cvFile.setRefusReason(reason);
		return new CvFileDTO(cvRepository.save(cvFile));
	}

	public List<CvFileDTO> getCvFilesWithState(CvState state) {
		List<CvFile> cvFiles = cvRepository.findAllByCvState(state);
		return cvFiles.stream().map(CvFileDTO::new).collect(Collectors.toList());
	}

	public CvFileDTO updateCvState(Long id, CvState newState, String reason) {
		CvFile cvFile = cvRepository.findById(id)
				.orElseThrow(CvFileNotFoundException::new);
		cvFile.setCvState(newState);
		cvFile.setRefusReason(reason);
		return new CvFileDTO(cvRepository.save(cvFile));
	}

//	Job Offer

	public List<JobOfferDTO> getAllJobOffer(){
		return jobOfferRepository.findAll().stream().map(JobOfferDTO::new).toList();
	}

	public JobOfferDTO getJobOfferByID(Long id){
		return new JobOfferDTO(jobOfferRepository.findById(id)
				.orElseThrow(() -> new JobOfferNotFoundException(id)));
	}

	public List<JobOfferDTO> getJobOffersWithState(JobOfferState state) {
		return jobOfferRepository.findJobOfferByJobOfferState(state)
				.stream().map(JobOfferDTO::new).collect(Collectors.toList());
	}

	public JobOfferDTO updateJobOfferState(Long id, JobOfferState newState, String reason) {
		JobOffer jobOffer = jobOfferRepository.findById(id)
				.orElseThrow(() -> new JobOfferNotFoundException(id));
		jobOffer.setJobOfferState(newState);
		jobOffer.setRefusReason(reason);
		return new JobOfferDTO(jobOfferRepository.save(jobOffer));
	}

	public void deleteJobOffer(Long id){
		jobOfferRepository.deleteById(id);
	}

}

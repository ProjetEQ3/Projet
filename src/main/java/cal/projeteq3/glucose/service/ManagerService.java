package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.exception.request.CvFileNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.exception.request.ManagerNotFoundException;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.repository.CvRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService{

	private final ManagerRepository managerRepository;
	private final StudentRepository studentRepository;
	private final JobOfferRepository jobOfferRepository;
	private final CvRepository cvRepository;

	@Autowired
	public ManagerService(
		ManagerRepository managerRepository, StudentRepository studentRepository,
		JobOfferRepository jobOfferRepository, CvRepository cvRepository
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

    public Optional<ManagerDTO> getManagerByID(Long id) {
        Optional<Manager> managerOptional = managerRepository.findById(id);
		return managerOptional.map(ManagerDTO::new);
    }

    public ManagerDTO updateManager(Long id, ManagerDTO updatedManager) {
        Optional<Manager> existingManager = managerRepository.findById(id);
        if(existingManager.isPresent()) {
            Manager manager = existingManager.get();

            manager.setFirstName(updatedManager.getFirstName());
            manager.setLastName(updatedManager.getLastName());
            manager.setEmail(updatedManager.getEmail());

            return new ManagerDTO(managerRepository.save(manager));
        }

		throw new ManagerNotFoundException(id);
    }

	public void deleteManager(Long id){
		managerRepository.deleteById(id);
	}

//    CV File
	public CvFileDTO getCvByID(Long id){
		return new CvFileDTO(cvRepository.findById(id).orElseThrow());
	}

	public List<CvFileDTO> getAllCv(){
		return cvRepository.findAll().stream().map(CvFileDTO::new).toList();
	}

	public List<CvFileDTO> getPendingCv(){
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
		Optional<CvFile> existingCvFile = cvRepository.findById(id);
		if(existingCvFile.isPresent()) {
			CvFile cvFile = existingCvFile.get();
			cvFile.setCvState(CvState.REFUSED);
			cvFile.setRefusReason(reason);
			return new CvFileDTO(cvRepository.save(cvFile));
		}
		throw new JobOffreNotFoundException(id);
	}
//	Job Offer

	public List<JobOfferDTO> getAllJobOffer(){
		return jobOfferRepository.findAll().stream().map(JobOfferDTO::new).toList();
	}

	public JobOfferDTO getJobOfferByID(Long id){
		return new JobOfferDTO(jobOfferRepository.findById(id).orElseThrow());
	}

	public List<JobOfferDTO> getJobOffersWithState(JobOfferState state) {
		List<JobOffer> jobOffers = jobOfferRepository.findJobOfferByJobOfferState(state);
		return jobOffers.stream().map(JobOfferDTO::new).collect(Collectors.toList());
	}

	public JobOfferDTO updateJobOfferState(Long id, JobOfferState newState) {
		Optional<JobOffer> existingJobOffer = jobOfferRepository.findById(id);
		if(existingJobOffer.isPresent()) {
			JobOffer jobOffer = existingJobOffer.get();
			jobOffer.setJobOfferState(newState);
			return new JobOfferDTO(jobOfferRepository.save(jobOffer));
		}

		throw new JobOffreNotFoundException(id);
	}

<<<<<<< HEAD
	public void acceptCv(Long id){
		throw new UnsupportedOperationException();
	}

	public void refuseCv(Long id){
		throw new UnsupportedOperationException();
=======
	public CvFileDTO updateCvState(Long id, CvState newState) {
		Optional<CvFile> existingCvFile = cvRepository.findById(id);
		if(existingCvFile.isPresent()) {
			CvFile cvFile = existingCvFile.get();
			cvFile.setCvState(newState);
			return new CvFileDTO(cvRepository.save(cvFile));
		}

		throw new CvFileNotFoundException();
	}

	public List<CvFileDTO> getCvFilesWithState(CvState state) {
		List<CvFile> cvFiles = cvRepository.findAllByCvState(state);
		return cvFiles.stream().map(CvFileDTO::new).collect(Collectors.toList());
	}

	public void deleteJobOffer(Long id){
		jobOfferRepository.deleteById(id);
>>>>>>> 6e9c5189c00ca0f006b412f0bc9fec40214bc919
	}

}

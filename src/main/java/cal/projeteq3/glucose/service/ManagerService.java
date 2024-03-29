package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.evaluation.WorkEnvironmentDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.badRequestException.*;
import cal.projeteq3.glucose.exception.unauthorizedException.WorkEnvironmentEvaluationAlreadyExistsException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.contract.Signature;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation.WorkEnvironmentEvaluation;
import cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation.sections.WorkEnvironment;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ManagerService{
	private final ManagerRepository managerRepository;
	private final StudentRepository studentRepository;
	private final JobOfferRepository jobOfferRepository;
	private final CvFileRepository cvRepository;
	private final ContractRepository contractRepository;
	private final SignatureRepository signatureRepository;
	private final JobApplicationRepository jobApplicationRepository;
	private final WorkEnvironmentEvaluationRepository workEnvironmentEvaluationRepository;

	// database operations here

	public ManagerDTO createManager(ManagerDTO managerDTO){
		return new ManagerDTO(managerRepository.save(managerDTO.toEntity()));
	}

	public List<ManagerDTO> getAllManagers(){
		List<Manager> managers = managerRepository.findAll();
		return managers.stream().map(ManagerDTO::new).collect(toList());
	}

	public ManagerDTO getManagerByID(Long id){
		return new ManagerDTO(managerRepository.findById(id).orElseThrow(UserNotFoundException::new));
	}

	public ManagerDTO updateManager(Long id, ManagerDTO updatedManager){
		Manager manager = managerRepository.findById(id).orElseThrow(() -> new ManagerNotFoundException(id));

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

	public List<CvFileDTO> getAllCv(Department department){
		List<CvFile> cvFiles = cvRepository.findAll();
		return cvFiles.stream().filter(cvFile -> cvFile.getStudent().getDepartment() == department).map(CvFileDTO::new).toList();
	}

	public List<CvFileDTO> getSubmittedCv(Department department){
		List<CvFile> cvFiles = cvRepository.findAllByCvState(CvState.SUBMITTED);
		return cvFiles.stream().filter(cvFile -> cvFile.getStudent().getDepartment() == department).map(CvFileDTO::new).toList();
	}

	public List<CvFileDTO> getAllCvFileByStudent(Long id){
		return cvRepository.findAllByStudent(studentRepository.findById(id).orElseThrow()).stream().map(CvFileDTO::new)
		                   .toList();
	}

	public List<CvFileDTO> getAllCvFileByStudentMatricule(String matricule){
		return cvRepository.findAllByStudent(studentRepository.findByMatricule(matricule)).stream().map(CvFileDTO::new)
		                   .toList();
	}

	public CvFileDTO getCvFileByStudentAndFileName(String matricule, String fileName){
		return new CvFileDTO(cvRepository.findByStudentAndFileName(studentRepository.findByMatricule(matricule),
		                                                           fileName));
	}

	public void deleteCvFile(Long id){
		cvRepository.deleteById(id);
	}

	public void deleteAllCvFileByStudentMatricule(String matricule){
		cvRepository.deleteAllByStudent(studentRepository.findByMatricule(matricule));
	}

	public List<CvFileDTO> getCvFilesWithState(CvState state, Department department){
		List<CvFile> cvFiles = cvRepository.findAllByCvState(state);
		return cvFiles.stream().filter(cvFile -> cvFile.getStudent().getDepartment() == department).map(CvFileDTO::new)
		               .toList();
	}

	public CvFileDTO updateCvState(Long id, CvState newState, String reason){
		CvFile cvFile = cvRepository.findById(id).orElseThrow(CvFileNotFoundException::new);
		cvFile.setCvState(newState);
		cvFile.setRefusReason(reason);
		return new CvFileDTO(cvRepository.save(cvFile));
	}

	//	Job Offer

	public List<JobOfferDTO> getAllJobOffer(Semester semester, Department department){
		List<JobOffer> jobOffers = jobOfferRepository.findAllBySemester(semester);
		return jobOffers.stream().filter(jobOffer -> jobOffer.getDepartment() == department).map(JobOfferDTO::new)
		                .toList();
	}

	public JobOfferDTO getJobOfferByID(Long id){
		return new JobOfferDTO(jobOfferRepository.findById(id).orElseThrow(() -> new JobOfferNotFoundException(id)));
	}

	public List<JobOfferDTO> getJobOffersWithState(JobOfferState state, Semester semester, Department department){
		List<JobOffer> jobOffers = jobOfferRepository.findJobOfferByJobOfferStateAndSemester(state, semester);
		return jobOffers.stream().filter(jobOffer -> jobOffer.getDepartment() == department).map(JobOfferDTO::new)
		                .toList();
	}

	public JobOfferDTO updateJobOfferState(Long id, JobOfferState newState, String reason){
		JobOffer jobOffer = jobOfferRepository.findById(id).orElseThrow(() -> new JobOfferNotFoundException(id));
		jobOffer.setJobOfferState(newState);
		jobOffer.setRefusReason(reason);
		return new JobOfferDTO(jobOfferRepository.save(jobOffer));
	}

	public void deleteJobOffer(Long id){
		jobOfferRepository.deleteById(id);
	}

	public List<ContractDTO> getContractsBySession(Semester semester, Department department){
		Manager manager = managerRepository.findAll().get(0);
		List<Contract> contracts = contractRepository.findAllByJobOffer_Semester(semester);
		return contracts.stream().filter(contract -> contract.getJobOffer().getDepartment() == department)
		                .map((contract -> new ContractDTO(contract, manager))).collect(toList());
	}

	public ContractDTO signContract(Long contractId, Long managerId){
		Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFoundException(managerId));
		Contract contract = contractRepository.findById(contractId).orElseThrow(ContractNotFoundException::new);
		if(!contract.isReadyToSign()) throw new ContractNotReadyToSignException();
		if(contract.getManagerSignature() != null) throw new ContractAlreadySignedException();
		if(manager.getFirstName() == null || manager.getLastName() == null) throw new ManagerNotCompleteException();
		if(!contract.isReadyToSign()) throw new ContractNotReadyToSignException();
		if(contract.getStudentSignature() == null) throw new ContractNotSignedByStudentException();
		if(contract.getEmployerSignature() == null) throw new ContractNotSignedByEmployerException();
		Signature signature = signatureRepository.save(Signature
				.builder()
				.firstName(manager.getFirstName())
				.lastName(manager.getLastName())
				.signatureDate(java.time.LocalDate.now())
				.contract(contract)
				.build());
		contract.setManagerSignature(signature);
		return new ContractDTO(contractRepository.save(contract), manager);
	}

	public List<StudentDTO> getStudents(Department department){
		List<StudentDTO> students = studentRepository.findAllByDepartment(department).stream().map(StudentDTO::new).toList();
		for (StudentDTO student : students) {
			loadJobApplications(student);
			student.setStudentState(jobApplicationRepository);
		}
		return students;
	}

	public List<JobApplicationDTO> getJobApplicationsByStudentId(Long id){
		return jobApplicationRepository.findAllByStudentId(id).stream().map(JobApplicationDTO::new).toList();
	}

	public JobApplicationDTO saveWorkEnvironmentEvaluationWithJobApplicationId(Long jobApplicationId, WorkEnvironmentDTO workEnvironmentDTO){
		if (workEnvironmentEvaluationRepository.existsByJobApplicationId(jobApplicationId)) throw new WorkEnvironmentEvaluationAlreadyExistsException();
		JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId).orElseThrow(JobApplicationNotFoundException::new);
		WorkEnvironmentEvaluation workEnvironmentEvaluation = new WorkEnvironmentEvaluation();
		workEnvironmentEvaluation.setJobApplication(jobApplication);
		WorkEnvironment workEnvironment = getWorkEnvironment(workEnvironmentDTO);
		workEnvironmentEvaluation.setWorkEnvironment(workEnvironment);
		workEnvironmentEvaluationRepository.save(workEnvironmentEvaluation);
		return new JobApplicationDTO(jobApplicationRepository.save(jobApplication));
	}

	private static WorkEnvironment getWorkEnvironment(WorkEnvironmentDTO workEnvironmentDTO) {
		WorkEnvironment workEnvironment = new WorkEnvironment();
		workEnvironment.setAdequateEquipment(workEnvironmentDTO.getAdequateEquipment());
		workEnvironment.setCompetitiveSalary(workEnvironmentDTO.getCompetitiveSalary());
		workEnvironment.setEffectiveCommunication(workEnvironmentDTO.getEffectiveCommunication());
		workEnvironment.setPositiveAtmosphere(workEnvironmentDTO.getPositiveAtmosphere());
		workEnvironment.setReasonableWorkload(workEnvironmentDTO.getReasonableWorkload());
		workEnvironment.setSafetyCompliance(workEnvironmentDTO.getSafetyCompliance());
		workEnvironment.setSufficientSupervision(workEnvironmentDTO.getSufficientSupervision());
		workEnvironment.setTaskConformity(workEnvironmentDTO.getTaskConformity());
		workEnvironment.setWelcomeMeasures(workEnvironmentDTO.getWelcomeMeasures());
		return workEnvironment;
	}

	private void loadJobApplications(StudentDTO student) {
		student.setJobApplications(jobApplicationRepository.findAllByStudentId(student.getId())
				.stream()
				.map(JobApplication::getId)
				.collect(Collectors.toList()));
	}
}

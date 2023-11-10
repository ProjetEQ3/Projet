package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.badRequestException.*;
import cal.projeteq3.glucose.exception.unauthorizedException.JobOfferNotOpenException;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.contract.Signature;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployerService{
	private final JobOfferRepository jobOfferRepository;
	private final EmployerRepository employerRepository;
	private final JobApplicationRepository jobApplicationRepository;
	private final AppointmentRepository appointmentRepository;
	private final ContractRepository contractRepository;
	private final PasswordEncoder passwordEncoder;
	private final ManagerRepository managerRepository;
	private final SignatureRepository signatureRepository;

	// database operations here

	public EmployerDTO createEmployer(RegisterEmployerDTO registerEmployerDTO) {
		Employer employer = Employer.builder()
				.email(registerEmployerDTO.getRegisterDTO().getEmail())
				.password(passwordEncoder.encode(registerEmployerDTO.getRegisterDTO().getPassword()))
				.firstName(registerEmployerDTO.getEmployerDTO().getFirstName())
				.lastName(registerEmployerDTO.getEmployerDTO().getLastName())
				.organisationName(registerEmployerDTO.getEmployerDTO().getOrganisationName())
				.organisationPhone(registerEmployerDTO.getEmployerDTO().getOrganisationPhone())
				.build();
        return new EmployerDTO(employerRepository.save(employer));
    }

	public List<EmployerDTO> getAllEmployers(){
		return employerRepository.findAll()
				.stream().map(EmployerDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public EmployerDTO getEmployerByEmail(String email) {
		Optional<Employer> employerOptional = employerRepository.findByCredentialsEmail(email);
		Employer employer = employerOptional.orElseThrow(() -> new EmployerNotFoundException(0L));

		employer.getJobOffers().size();

		return new EmployerDTO(employer);
	}

	public EmployerDTO getEmployerByID(Long id){
		return new EmployerDTO(employerRepository.findById(id).orElseThrow(
				() -> new EmployerNotFoundException(id)
		));
	}

	public EmployerDTO updateEmployer(Long id, EmployerDTO updatedEmployer){
		Employer employer = employerRepository.findById(id)
				.orElseThrow(() -> new EmployerNotFoundException(id));

		employer.setId(updatedEmployer.getId());
		employer.setFirstName(updatedEmployer.getFirstName());
		employer.setLastName(updatedEmployer.getLastName());
		employer.setEmail(updatedEmployer.getEmail());
		employer.setOrganisationName(updatedEmployer.getOrganisationName());
		employer.setOrganisationPhone(updatedEmployer.getOrganisationPhone());

		return new EmployerDTO(employerRepository.save(employer));

	}

	public void deleteEmployer(Long id){
		employerRepository.deleteById(id);
	}

	public List<JobOfferDTO> getJobOffersDTOByEmployerId(Long employerId, Semester semester) {
		Employer employer = employerRepository
				.findById(employerId)
				.orElseThrow(() -> new EmployerNotFoundException(employerId));

		return employer.getJobOffers().stream()
				.filter(jobOffer -> jobOffer.getSemester().equals(semester))
				.map(JobOfferDTO::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public JobOfferDTO createJobOffer(JobOfferDTO jobOffer, Long employerId){
		Employer employer = employerRepository
			.findById(employerId)
			.orElseThrow(() -> new EmployerNotFoundException(employerId));
		JobOffer jobOfferEntity = jobOffer.toEntity();

		jobOfferEntity.setSemester(new Semester(jobOffer.getStartDate()));

		employer.addJobOffer(jobOfferEntity);
		JobOfferDTO result = new JobOfferDTO(jobOfferRepository.save(jobOfferEntity));
		employerRepository.save(employer);
		return result;
	}

	public JobOfferDTO updateJobOffer(JobOfferDTO updatedJobOffer){
		JobOffer jobOffer = jobOfferRepository.findById(updatedJobOffer.getId())
			.orElseThrow(() -> new JobOfferNotFoundException(updatedJobOffer.getId()));

		jobOffer.copy(updatedJobOffer.toEntity());
		jobOffer.setSemester(new Semester(jobOffer.getStartDate()));

		return new JobOfferDTO(jobOfferRepository.save(jobOffer));
	}

	public void deleteJobOffer(Long id){
		jobOfferRepository.deleteById(id);
	}

	public List<JobOfferDTO> getAllJobOffers(Long employerId, Semester semester){
		List<JobOffer> jobOffers = jobOfferRepository.findJobOfferByEmployer_IdAndSemester(employerId, semester);
		if(jobOffers.isEmpty()) return Collections.emptyList();
		return jobOffers.stream().map(JobOfferDTO::new).collect(Collectors.toList());
	}

	public JobApplicationDTO acceptApplication(Long applicationId){
		JobApplication application = jobApplicationRepository.findById(applicationId)
				.orElseThrow(JobApplicationNotFoundException::new);

		if (!application.getJobOffer().isHiring()) throw new JobOfferNotOpenException();
		if (application.isImmutable()) throw new JobApplicationHasAlreadyADecision();

		application.setJobApplicationState(JobApplicationState.ACCEPTED);
		jobApplicationRepository.save(application);
		createNewContract(application);
		return new JobApplicationDTO(application);
	}

	private void createNewContract(JobApplication application) {
		JobOffer jobOffer = application.getJobOffer();
		Student student = application.getStudent();
		Employer employer = jobOffer.getEmployer();

		Contract contract = new Contract(employer, student, jobOffer);
		contractRepository.save(contract);
	}

	public JobApplicationDTO refuseApplication(Long applicationId){
		JobApplication application = jobApplicationRepository
				.findById(applicationId)
				.orElseThrow(JobApplicationNotFoundException::new);

		if (application.isImmutable()) throw new JobApplicationHasAlreadyADecision();

		application.setJobApplicationState(JobApplicationState.REJECTED);

		jobApplicationRepository.save(application);
		return new JobApplicationDTO(application);
	}

	public List<JobApplicationDTO> getPendingApplicationsByJobOfferId(Long jobOfferId) {
		JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
			.orElseThrow(() -> new JobOfferNotFoundException(jobOfferId));
		List<JobApplication> jobApplications = jobOffer.getJobApplications();
		if(jobApplications.isEmpty()) return Collections.emptyList();
		return jobOffer.getJobApplications().stream()
			.filter(jobApplication -> jobApplication.getJobApplicationState().equals(JobApplicationState.SUBMITTED))
			.map(JobApplicationDTO::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public JobApplicationDTO addAppointmentByJobApplicationId(Long jobApplicationId, Set<LocalDateTime> dates){
		List<Appointment> appointmentList = dates.stream()
				.map(time -> {
					Appointment appointment = new Appointment();
					appointment.setAppointmentDate(time);
					return appointment;
				})
				.toList();

		JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
				.orElseThrow(JobApplicationNotFoundException::new);

		for(Appointment app : appointmentList){
			app.setJobApplication(jobApplication);
			jobApplication.addAppointment(app);
			appointmentRepository.save(app);
		}
		jobApplication.setJobApplicationState(JobApplicationState.CONVOKED);
		jobApplicationRepository.save(jobApplication);
		if (appointmentList.size() == 1) {
			appointmentList.get(0).setChosen(true);
			appointmentRepository.save(appointmentList.get(0));
			jobApplication.setJobApplicationState(JobApplicationState.WAITING_APPOINTMENT);
			jobApplicationRepository.save(jobApplication);
		}
		return jobApplicationRepository.findById(jobApplication.getId())
				.map(JobApplicationDTO::new)
				.orElseThrow(JobApplicationNotFoundException::new);
	}

	public List<JobApplicationDTO> getAllJobApplicationsByEmployerId(Long employerId){
		return jobApplicationRepository.findAllByJobOffer_Employer_Id(employerId).stream().map(JobApplicationDTO::new).collect(Collectors.toList());
	}

	public List<StudentDTO> getWaitingStudents(Long employerId, Semester semester) {
		return jobApplicationRepository.findAllByJobOffer_Employer_Id(employerId).stream()
				.filter(jobApplication -> jobApplication.getJobOffer().getSemester().equals(semester))
				.filter(jobApplication -> jobApplication.getJobApplicationState().equals(JobApplicationState.WAITING_APPOINTMENT))
				.map(jobApplication -> new StudentDTO(jobApplication.getStudent(), jobApplication.getId()))
				.collect(Collectors.toList());
	}

	public JobOfferDTO getOfferByApplicationId(Long applicationId) {
		return jobApplicationRepository.findById(applicationId)
				.map(JobApplication::getJobOffer)
				.map(JobOfferDTO::new)
				.orElseThrow(() -> new JobOfferNotFoundException(0L));
	}

    public AppointmentDTO getAppointmentByJobApplicationId(Long applicationId) {
		List<Appointment> appointments = jobApplicationRepository.findAppointmentsByJobApplicationId(applicationId).stream().filter(Appointment::isChosen).toList();
		return appointments.isEmpty() ? null : new AppointmentDTO(appointments.get(0));
    }

	public List<ContractDTO> getContractsBySession(Semester semester, Long employerId) {
		Manager manager = managerRepository.findAll().get(0);
		List<Contract> contracts = new ArrayList<>(contractRepository.findAllByJobOffer_Semester(semester).stream().toList());
        contracts.removeIf(contract -> !contract.getEmployer().getId().equals(employerId));
		return contracts.stream().map((contract -> new ContractDTO(contract, manager))).collect(Collectors.toList());
	}

	public ContractDTO signContract(Long contractId, Long employerId){
		Employer employer = employerRepository.findById(employerId).orElseThrow(() -> new EmployerNotFoundException(employerId));
		Contract contract = contractRepository.findById(contractId).orElseThrow(ContractNotFoundException::new);
		if(!contract.isReadyToSign()) throw new ContractNotReadyToSignException();
		if(!contract.getEmployer().getId().equals(employerId)) throw new UnauthorizedContractToSignException();
		if(contract.getEmployerSignature() != null) throw new ContractAlreadySignedException();
		if(employer.getFirstName() == null || employer.getLastName() == null) throw new EmployerNotCompleteException();
		Signature signature = signatureRepository.save(Signature
				.builder()
				.firstName(employer.getFirstName())
				.lastName(employer.getLastName())
				.signatureDate(java.time.LocalDate.now())
				.contract(contract)
				.build());
		contract.setEmployerSignature(signature);
		return new ContractDTO(contractRepository.save(contract), managerRepository.findFirstByDepartment(contract.getJobOffer().getDepartment()));
	}

	public int nbSubmittedApplicationsAcrossAllJobOffersFromEmployer(Long employerId) {
		return jobOfferRepository.countAllByEmployer_IdAndJobApplications_JobApplicationState(employerId, JobApplicationState.SUBMITTED);
	}

	public List<JobOfferDTO> getAllJobOffersWithSubmittedApplicationsFromEmployer(Long employerId) {
		return employerRepository.findById(employerId).orElseThrow(() -> new EmployerNotFoundException(employerId))
				.getJobOffers().stream()
				.filter(jobOffer -> jobOffer.getJobApplications().stream()
						.anyMatch(application -> application.getJobApplicationState() == JobApplicationState.SUBMITTED))
				.map(JobOfferDTO::new)
				.collect(Collectors.toList());
	}

}

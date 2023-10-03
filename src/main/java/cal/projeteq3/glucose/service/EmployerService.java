package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.contract.CreateContractDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.exception.request.AddressNotFoundException;
import cal.projeteq3.glucose.exception.request.EmployerNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.exception.request.SupervisorNotFoundException;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployerService{

	private final JobOfferRepository jobOfferRepository;
	private final EmployerRepository employerRepository;
	private final ContractRepository contractRepository;
	private final SupervisorRepository supervisorRepository;
	private final AddressRepository addressRepository;

	@Autowired
	public EmployerService(EmployerRepository employerRepository, JobOfferRepository jobOfferRepository, ContractRepository contractRepository, SupervisorRepository supervisorRepository, AddressRepository addressRepository){
		this.jobOfferRepository = jobOfferRepository;
		this.employerRepository = employerRepository;
		this.contractRepository = contractRepository;
		this.supervisorRepository = supervisorRepository;
		this.addressRepository = addressRepository;
	}

	// database operations here

	public EmployerDTO createEmployer(RegisterEmployerDTO registerEmployerDTO) {
		Employer employer = Employer.builder()
				.email(registerEmployerDTO.getRegisterDTO().getEmail())
				.password(registerEmployerDTO.getRegisterDTO().getPassword())
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


	public List<JobOfferDTO> getJobOffersDTOByEmployerId(Long employerId) {
		Employer employer = employerRepository.findById(employerId)
				.orElseThrow(() -> new EmployerNotFoundException(employerId));
		return employer.getJobOffers().stream().map(JobOfferDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public JobOfferDTO createJobOffer(JobOfferDTO jobOffer, Long employerId){
		Employer employer = employerRepository.findById(employerId)
				.orElseThrow(() -> new EmployerNotFoundException(employerId));

		JobOffer jobOfferEntity = jobOffer.toEntity();
		employer.addJobOffer(jobOfferEntity);
		JobOfferDTO result = new JobOfferDTO(jobOfferRepository.save(jobOfferEntity));
		employerRepository.save(employer);

		return result;

	}

	public JobOfferDTO updateJobOffer(JobOfferDTO updatedJobOffer){
		JobOffer jobOffer = jobOfferRepository.findById(updatedJobOffer.getId())
				.orElseThrow(() -> new JobOffreNotFoundException(updatedJobOffer.getId()));

		jobOffer.copy(updatedJobOffer.toEntity());
		return new JobOfferDTO(jobOfferRepository.save(jobOffer));
	}

	public void deleteJobOffer(Long id){
		jobOfferRepository.deleteById(id);
	}

	public List<JobOfferDTO> getAllJobOffers(Long employerId){
		List<JobOffer> jobOffers = jobOfferRepository.findJobOfferByEmployer_Id(employerId);
		if(jobOffers.isEmpty()) return Collections.emptyList();
		return jobOffers.stream().map(JobOfferDTO::new).collect(Collectors.toList());
	}

	public ContractDTO createContract(CreateContractDTO createContractDTO){
		JobOffer jobOffer = jobOfferRepository.findById(createContractDTO.getJobOfferId())
				.orElseThrow(() -> new JobOffreNotFoundException(createContractDTO.getJobOfferId()));

		return new ContractDTO(contractRepository.save(
				Contract.builder()
						.employer(jobOffer.getEmployer())
						.supervisor(supervisorRepository.findById(createContractDTO.getSupervisorId())
								.orElseThrow(() -> new SupervisorNotFoundException(createContractDTO.getSupervisorId())))
						.workAddress(addressRepository.findById(createContractDTO.getWorkAddressId())
								.orElseThrow(() -> new AddressNotFoundException("\n\t ID:" + createContractDTO.getWorkAddressId())))
						.student(jobOffer.getJobApplications().stream()
								.filter(jobApplication -> jobApplication.getJobApplicationState()
										.equals(JobApplicationState.ACCEPTED)).findFirst()
								.orElseThrow(() -> new NoSuchElementException("No accepted job application found")).getStudent())
						.jobTitle(jobOffer.getTitle())
						.responsibilities(createContractDTO.getResponsibilities())
						.startDate(createContractDTO.getStartDate())
						.endDate(createContractDTO.getEndDate())
						.duration(createContractDTO.getDuration())
						.hoursPerWeek(jobOffer.getHoursPerWeek())
						.startShiftTime(createContractDTO.getStartShiftTime())
						.endShiftTime(createContractDTO.getEndShiftTime())
						.hoursPerDay(createContractDTO.getHoursPerDay())
						.employmentType(createContractDTO.getEmploymentType())
						.workDays(createContractDTO.getWorkDays())
						.hourlyRate(jobOffer.getSalary())
				.build()));
	}
}

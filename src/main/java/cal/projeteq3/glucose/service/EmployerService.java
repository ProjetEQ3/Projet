package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.badRequestException.EmployerNotFoundException;
import cal.projeteq3.glucose.exception.badRequestException.JobApplicationNotFoundException;
import cal.projeteq3.glucose.exception.badRequestException.JobOfferNotFoundException;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.repository.AppointmentRepository;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.JobApplicationRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployerService{
	private final JobOfferRepository jobOfferRepository;
	private final EmployerRepository employerRepository;
	private final JobApplicationRepository jobApplicationRepository;
	private final AppointmentRepository appointmentRepository;
	private final PasswordEncoder passwordEncoder;

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
		JobApplication application = jobApplicationRepository
			.findById(applicationId)
			.orElseThrow(JobApplicationNotFoundException::new);
		application.setJobApplicationState(JobApplicationState.ACCEPTED);
		jobApplicationRepository.save(application);
		return new JobApplicationDTO(application);
	}

	public JobApplicationDTO refuseApplication(Long applicationId){
		JobApplication application = jobApplicationRepository
				.findById(applicationId)
				.orElseThrow(JobApplicationNotFoundException::new);
		application.setJobApplicationState(JobApplicationState.REJECTED);
		jobApplicationRepository.save(application);
		return new JobApplicationDTO(application);
	}

	public List<StudentDTO> getPendingStudentsByJobOfferId(Long jobOfferId) {
		JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
			.orElseThrow(() -> new JobOfferNotFoundException(jobOfferId));
		List<JobApplication> jobApplications = jobOffer.getJobApplications();
		if(jobApplications.isEmpty()) return Collections.emptyList();
		return jobOffer.getJobApplications().stream()
			.filter(jobApplication -> jobApplication.getJobApplicationState().equals(JobApplicationState.SUBMITTED))
			.map(jobApplication -> new StudentDTO(jobApplication.getStudent(), jobApplication.getId()))
			.collect(Collectors.toList());
	}

	//EQ3-17
	public JobApplicationDTO addAppointmentByJobApplicationId(Long jobApplicationId, List<LocalDateTime> dates){
		List<Appointment> appointmentList = dates.stream()
				.map(time -> {
					Appointment appointment = new Appointment();
					appointment.setAppointmentDate(time);
					return appointment;
				})
				.toList();

		JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
			.orElseThrow(() -> new JobApplicationNotFoundException(jobApplicationId));
		for(Appointment app : appointmentList){
			app.setJobApplication(jobApplication);
			jobApplication.addAppointment(app);
			appointmentRepository.save(app);
		}
		jobApplication.setJobApplicationState(JobApplicationState.CONVOKED);
		jobApplicationRepository.save(jobApplication);
		return jobApplicationRepository.findById(jobApplication.getId())
			.map(JobApplicationDTO::new)
			.orElseThrow(() -> new JobApplicationNotFoundException(jobApplicationId));
	}
}

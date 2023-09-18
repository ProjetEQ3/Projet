package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.exception.request.EmployerNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployerService{

	private final JobOfferRepository jobOfferRepository;
	private final EmployerRepository employerRepository;

	@Autowired
	public EmployerService(EmployerRepository employerRepository, JobOfferRepository jobOfferRepository){
		this.jobOfferRepository = jobOfferRepository;
		this.employerRepository = employerRepository;
	}

	// database operations here

   public EmployerDTO createEmployer(Employer employer) {
        return new EmployerDTO(employerRepository.save(employer));
    }

	public List<EmployerDTO> getAllEmployers(){
		List<Employer> employers = employerRepository.findAll();
		return employers.stream().map(EmployerDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public EmployerDTO getEmployerByEmail(String email) {
		Optional<Employer> employerOptional = employerRepository.findByCredentialsEmail(email);
		Employer employer = employerOptional.orElseThrow(() -> new EmployerNotFoundException(0L));

		employer.getJobOffers().size();

		return new EmployerDTO(employer);
	}


	public List<JobOfferDTO> getJobOffersDTOByEmployerId(Long employerId) {
		Optional<Employer> employerOptional = employerRepository.findById(employerId);
		if (employerOptional.isPresent()) {
			Employer employer = employerOptional.get();
			return employer.getJobOffers().stream().map(JobOfferDTO::new).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public Optional<EmployerDTO> getEmployerByID(Long id){
		Optional<Employer> employerOptional = employerRepository.findById(id);
		return employerOptional.map(EmployerDTO::new);
	}

	public EmployerDTO updateEmployer(Long id, EmployerDTO updatedEmployer){
		Optional<Employer> existingEmployer = employerRepository.findById(id);
		if(existingEmployer.isPresent()){
			Employer employer = existingEmployer.get();

			employer.setId(updatedEmployer.getId());
			employer.setFirstName(updatedEmployer.getFirstName());
			employer.setLastName(updatedEmployer.getLastName());
			employer.setEmail(updatedEmployer.getEmail());
			employer.setOrganisationName(updatedEmployer.getOrganisationName());
			employer.setOrganisationPhone(updatedEmployer.getOrganisationPhone());

			return new EmployerDTO(employerRepository.save(employer));
		}

		throw new EmployerNotFoundException(id);
	}

	public void deleteEmployer(Long id){
		employerRepository.deleteById(id);
	}

	@Transactional
	public void createJobOffer(JobOfferDTO jobOffer, Long employerId){
		Optional<Employer> employerOptional = employerRepository.findById(employerId);
		if(employerOptional.isPresent()){
			Employer employer = employerOptional.get();
			employer.addJobOffer(jobOffer.toEntity());
			employerRepository.save(employer);
		} else
			throw new EmployerNotFoundException(employerId);
	}

	public JobOfferDTO updateJobOffer(JobOfferDTO updatedJobOffer){
		Optional<JobOffer> existingJobOffer = jobOfferRepository.findById(updatedJobOffer.getId());
		if(existingJobOffer.isPresent()){
			JobOffer jobOffer = existingJobOffer.get();
			jobOffer.copy(updatedJobOffer.toEntity());
			return new JobOfferDTO(jobOfferRepository.save(jobOffer));
		}
		throw new JobOffreNotFoundException(updatedJobOffer.getId());
	}

	public void deleteJobOffer(Long id){
		jobOfferRepository.deleteById(id);
	}

	public List<JobOfferDTO> getAllJobOffers(Long employerId){
		List<JobOffer> jobOffers = jobOfferRepository.findJobOfferByEmployer_Id(employerId);
		return jobOffers.stream().map(JobOfferDTO::new).collect(Collectors.toList());
	}
}

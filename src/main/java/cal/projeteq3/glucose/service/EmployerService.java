package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.EmployerDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.exception.request.EmployerNotFoundException;
import cal.projeteq3.glucose.model.Employer;
import cal.projeteq3.glucose.model.JobOffer;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployerService {

    private final JobOfferRepository jobOfferRepository;
    private final EmployerRepository employerRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository, JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.employerRepository = employerRepository;
    }

    // database operations here

    public EmployerDTO createEmployer(Employer employer) {
        return new EmployerDTO(employerRepository.save(employer));
    }

    public List<EmployerDTO> getAllEmployers() {
        List<Employer> employers = employerRepository.findAll();
        return employers.stream().map(EmployerDTO::new).collect(Collectors.toList());
    }

    public Optional<EmployerDTO> getEmployerByID(Long id) {
        Optional<Employer> employerOptional = employerRepository.findById(id);
        return employerOptional.map(EmployerDTO::new);
    }

    public EmployerDTO updateEmployer(Long id, EmployerDTO updatedEmployer) {
        Optional<Employer> existingEmployer = employerRepository.findById(id);
        if(existingEmployer.isPresent()) {
            Employer employer = existingEmployer.get();

            employer.setUserID(updatedEmployer.getId());
            employer.setFirstName(updatedEmployer.getFirstName());
            employer.setLastName(updatedEmployer.getLastName());
            employer.setEmail(updatedEmployer.getEmail());
            employer.setOrganisationName(updatedEmployer.getOrganisationName());
            employer.setOrganisationPhone(updatedEmployer.getOrganisationPhone());

            return new EmployerDTO(employerRepository.save(employer));
        }

        throw new EmployerNotFoundException(id);
    }

    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }

    /*public JobOfferDTO createJobOffer(JobOffer jobOffer){
        return new JobOfferDTO(jobOfferRepository.save(jobOffer));
    }*/

    public JobOfferDTO createJobOffer(JobOfferDTO jobOffer){
        return null; //TODO plz fix with DTO
    }

   /* public JobOfferDTO updateJobOffer(Long id, JobOffer updatedJobOffer){
        Optional<JobOffer> existingJobOffer = jobOfferRepository.findById(id);
        if (existingJobOffer.isPresent()){
            JobOffer jobOffer = existingJobOffer.get();

            jobOffer.setState(updatedJobOffer.getState());
            jobOffer.setTitle(updatedJobOffer.getTitle());
            jobOffer.setDepartment(updatedJobOffer.getDepartment());
            jobOffer.setDescription(updatedJobOffer.getDescription());
            jobOffer.setLocation(updatedJobOffer.getLocation());
            jobOffer.setStartDate(updatedJobOffer.getStartDate());
            jobOffer.setHoursPerWeek(updatedJobOffer.getHoursPerWeek());
            jobOffer.setExpirationDate(updatedJobOffer.getExpirationDate());
            jobOffer.setSalary(updatedJobOffer.getSalary());
            jobOffer.setEmployer(updatedJobOffer.getEmployer());
            return new JobOfferDTO(jobOfferRepository.save(jobOffer));
        }

        throw new IllegalArgumentException("JobOffer with ID " + id + " does not exist.");
    }*/

    public JobOfferDTO updateJobOffer(Long id, JobOfferDTO updatedJobOffer){
        return null; //TODO plz fix with DTO
    }

    public void deleteJobOffer(Long id){
        jobOfferRepository.deleteById(id);
    }

    public List<JobOffer> getAllMyJobOffers(Employer employer){
        return jobOfferRepository.findAllByEmployer(employer);
    }

    public List<JobOfferDTO> getAllJobOffers() {
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        return jobOffers.stream().map(JobOfferDTO::new).collect(Collectors.toList());
    }
}

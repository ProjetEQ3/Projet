package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.EmployerDTO;
import cal.projeteq3.glucose.mapper.EmployerMapper;
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
    private final EmployerMapper employerMapper;

    @Autowired
    public EmployerService(EmployerRepository employerRepository, JobOfferRepository jobOfferRepository, EmployerMapper employerMapper) {
        this.jobOfferRepository = jobOfferRepository;
        this.employerRepository = employerRepository;
        this.employerMapper = employerMapper;
    }

    // database operations here

    public EmployerDTO createEmployeur(Employer employer) {
        return employerMapper.toDTO(employerRepository.save(employer));
    }

    public List<EmployerDTO> getAllEmployeurs() {
        List<Employer> employers = employerRepository.findAll();
        return employers.stream().map(employerMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<EmployerDTO> getEmployeurByID(Long id) {
        Optional<Employer> employerOptional = employerRepository.findById(id);
        return employerOptional.map(employerMapper::toDTO);
    }

    public EmployerDTO updateEmployeur(Long id, Employer updatedEmployer) {
        Optional<Employer> existingEmployeur = employerRepository.findById(id);
        if(existingEmployeur.isPresent()) {
            Employer employer = existingEmployeur.get();

            employer.setFirstName(updatedEmployer.getFirstName());
            employer.setLastName(updatedEmployer.getLastName());
            employer.setEmail(updatedEmployer.getEmail());
            employer.setPassword(updatedEmployer.getPassword());
            employer.setOrganisationName(updatedEmployer.getOrganisationName());
            employer.setOrganisationPhone(updatedEmployer.getOrganisationPhone());

            return employerMapper.toDTO(employerRepository.save(employer));
        }

        throw new IllegalArgumentException("Employer with ID " + id + " does not exist.");
    }

    public void deleteEmployeur(Long id) {
        employerRepository.deleteById(id);
    }

    public JobOffer createJobOffer(JobOffer jobOffer){
        return jobOfferRepository.save(jobOffer);
    }

    public JobOffer updateJobOffer(Long id, JobOffer updatedJobOffer){
        Optional<JobOffer> existingJobOffer = jobOfferRepository.findById(id);
        if (existingJobOffer.isPresent()){
            JobOffer jobOffer = existingJobOffer.get();

            jobOffer.setState(updatedJobOffer.getState());
            jobOffer.setTitle(updatedJobOffer.getTitle());
            jobOffer.setDepartment(updatedJobOffer.getDepartment());
            jobOffer.setDescription(updatedJobOffer.getDescription());
            jobOffer.setLocation(updatedJobOffer.getLocation());
            jobOffer.setStartDate(updatedJobOffer.getStartDate());
            jobOffer.setEndDate(updatedJobOffer.getEndDate());
            jobOffer.setHoursPerWeek(updatedJobOffer.getHoursPerWeek());
            jobOffer.setNbDaysToApply(updatedJobOffer.getNbDaysToApply());

            return jobOfferRepository.save(jobOffer);
        }

        throw new IllegalArgumentException("JobOffer with ID " + id + " does not exist.");
    }

    public void deleteJobOffer(Long id){
        jobOfferRepository.deleteById(id);
    }

    public List<JobOffer> getAllMyJobOffers(Employer employer){
        return jobOfferRepository.findAllByEmployer(employer);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }
}

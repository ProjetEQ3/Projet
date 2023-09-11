package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.Employer;
import cal.projeteq3.glucose.model.JobOffer;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeurService {

    private final EmployerRepository employerRepository;
    private final JobOfferRepository jobOfferRepository;

    @Autowired
    public EmployeurService(EmployerRepository employerRepository, JobOfferRepository jobOfferRepository) {
        this.employerRepository = employerRepository;
        this.jobOfferRepository = jobOfferRepository;
    }

    // database operations here

    public Employer createEmployeur(Employer employer) {
        return employerRepository.save(employer);
    }

    public List<Employer> getAllEmployeurs() {
        return employerRepository.findAll();
    }

    public Optional<Employer> getEmployeurByID(Long id) {
        return employerRepository.findById(id);
    }

    public Employer updateEmployeur(Long id, Employer updatedEmployer) {
        Optional<Employer> existingEmployeur = employerRepository.findById(id);
        if(existingEmployeur.isPresent()) {
            Employer employer = existingEmployeur.get();

            employer.setNom(updatedEmployer.getNom());
            employer.setPrenom(updatedEmployer.getPrenom());
            employer.setAdresseCourriel(updatedEmployer.getAdresseCourriel());
            employer.setMotDePasse(updatedEmployer.getMotDePasse());
            employer.setNomOrganisme(updatedEmployer.getNomOrganisme());
            employer.setNumTelephone(updatedEmployer.getNumTelephone());

            return employerRepository.save(employer);
        } else {
            throw new IllegalArgumentException("Employer with ID " + id + " does not exist.");
        }
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
        } else {
            throw new IllegalArgumentException("JobOffer with ID " + id + " does not exist.");
        }
    }

    public void deleteJobOffer(Long id){
        jobOfferRepository.deleteById(id);
    }

    public List<JobOffer> getAllMyJobOffers(Employer employer){
        return jobOfferRepository.findAllByEmployeur(employer);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }
}

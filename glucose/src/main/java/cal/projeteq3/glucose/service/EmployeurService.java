package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.Employeur;
import cal.projeteq3.glucose.model.JobOffer;
import cal.projeteq3.glucose.repository.EmployeurRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeurService {

    private final EmployeurRepository employeurRepository;
    private final JobOfferRepository jobOfferRepository;

    @Autowired
    public EmployeurService(EmployeurRepository employeurRepository, JobOfferRepository jobOfferRepository) {
        this.employeurRepository = employeurRepository;
        this.jobOfferRepository = jobOfferRepository;
    }

    // database operations here

    public Employeur createEmployeur(Employeur employeur) {
        return employeurRepository.save(employeur);
    }

    public List<Employeur> getAllEmployeurs() {
        return employeurRepository.findAll();
    }

    public Optional<Employeur> getEmployeurByID(Long id) {
        return employeurRepository.findById(id);
    }

    public Employeur updateEmployeur(Long id, Employeur updatedEmployeur) {
        Optional<Employeur> existingEmployeur = employeurRepository.findById(id);
        if(existingEmployeur.isPresent()) {
            Employeur employeur = existingEmployeur.get();

            employeur.setNom(updatedEmployeur.getNom());
            employeur.setPrenom(updatedEmployeur.getPrenom());
            employeur.setAdresseCourriel(updatedEmployeur.getAdresseCourriel());
            employeur.setMotDePasse(updatedEmployeur.getMotDePasse());
            employeur.setNomOrganisme(updatedEmployeur.getNomOrganisme());
            employeur.setNumTelephone(updatedEmployeur.getNumTelephone());

            return employeurRepository.save(employeur);
        } else {
            throw new IllegalArgumentException("Employeur with ID " + id + " does not exist.");
        }
    }

    public void deleteEmployeur(Long id) {
        employeurRepository.deleteById(id);
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

    public List<JobOffer> getAllMyJobOffers(Long idEmployeur){
        return jobOfferRepository.findAllByEmployeurId(idEmployeur);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }
}

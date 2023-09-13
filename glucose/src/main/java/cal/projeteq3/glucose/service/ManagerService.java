package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.ManagerDTO;
import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.model.Manager;
import cal.projeteq3.glucose.model.State;
import cal.projeteq3.glucose.repository.CvRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final JobOfferRepository jobOfferRepository;
    private final CvRepository cvRepository;


    @Autowired
    public ManagerService(ManagerRepository managerRepository, JobOfferRepository jobOfferRepository, CvRepository cvRepository) {
        this.managerRepository = managerRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.cvRepository = cvRepository;
    }

    // database operations here

    public ManagerDTO createGestionnaire(Manager manager) {
        return new ManagerDTO(managerRepository.save(manager));
    }

    public List<Manager> getAllGestionnaires() {
        return managerRepository.findAll();
    }

    public Optional<Manager> getGestionnaireByID(Long id) {
        return managerRepository.findById(id);
    }

    public Manager updateGestionnaire(Long id, Manager updatedManager) {
        Optional<Manager> existingGestionnaire = managerRepository.findById(id);
        if(existingGestionnaire.isPresent()) {
            Manager manager = existingGestionnaire.get();

            manager.setFirstName(updatedManager.getFirstName());
            manager.setLastName(updatedManager.getLastName());
            manager.setEmail(updatedManager.getEmail());
            manager.setPassword(updatedManager.getPassword());

            return managerRepository.save(manager);
        } else {
            throw new IllegalArgumentException("Manager with ID " + id + " does not exist.");
        }
    }

    public void deleteGestionnaire(Long id) {
        managerRepository.deleteById(id);
    }

    public List<JobOfferDTO> getAllJobOffer() {
        return jobOfferRepository.findAll().stream().map(JobOfferDTO::new).toList();
    }

    public JobOfferDTO getJobOfferByID(Long id) {
        return new JobOfferDTO(jobOfferRepository.findById(id).orElseThrow());
    }

    public List<CvFileDTO> getPendingCv(){
        return cvRepository.findAllByState(State.PENDING).stream().map(CvFileDTO::new).toList();
    }



}

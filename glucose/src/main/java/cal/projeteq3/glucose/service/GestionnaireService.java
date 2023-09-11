package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.Gestionnaire;
import cal.projeteq3.glucose.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestionnaireService {

    private final ManagerRepository managerRepository;

    @Autowired
    public GestionnaireService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    // database operations here

    public Gestionnaire createGestionnaire(Gestionnaire gestionnaire) {
        return managerRepository.save(gestionnaire);
    }

    public List<Gestionnaire> getAllGestionnaires() {
        return managerRepository.findAll();
    }

    public Optional<Gestionnaire> getGestionnaireByID(Long id) {
        return managerRepository.findById(id);
    }

    public Gestionnaire updateGestionnaire(Long id, Gestionnaire updatedGestionnaire) {
        Optional<Gestionnaire> existingGestionnaire = managerRepository.findById(id);
        if(existingGestionnaire.isPresent()) {
            Gestionnaire gestionnaire = existingGestionnaire.get();

            gestionnaire.setNom(updatedGestionnaire.getNom());
            gestionnaire.setPrenom(updatedGestionnaire.getPrenom());
            gestionnaire.setAdresseCourriel(updatedGestionnaire.getAdresseCourriel());
            gestionnaire.setMotDePasse(updatedGestionnaire.getMotDePasse());

            return managerRepository.save(gestionnaire);
        } else {
            throw new IllegalArgumentException("Gestionnaire with ID " + id + " does not exist.");
        }
    }

    public void deleteGestionnaire(Long id) {
        managerRepository.deleteById(id);
    }

}

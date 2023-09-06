package cal.projeteq3.glucose.services;

import cal.projeteq3.glucose.domain.Etudiant;
import cal.projeteq3.glucose.domain.Gestionnaire;
import cal.projeteq3.glucose.repositories.GestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestionnaireService {

    private final GestionnaireRepository gestionnaireRepository;

    @Autowired
    public GestionnaireService(GestionnaireRepository gestionnaireRepository) {
        this.gestionnaireRepository = gestionnaireRepository;
    }

    // database operations here

    public Gestionnaire createGestionnaire(Gestionnaire gestionnaire) {
        return gestionnaireRepository.save(gestionnaire);
    }

    public List<Gestionnaire> getAllGestionnaires() {
        return gestionnaireRepository.findAll();
    }

    public Optional<Gestionnaire> getGestionnaireByID(Long id) {
        return gestionnaireRepository.findById(id);
    }

    public Gestionnaire updateGestionnaire(Long id, Gestionnaire updatedGestionnaire) {
        Optional<Gestionnaire> existingGestionnaire = gestionnaireRepository.findById(id);
        if(existingGestionnaire.isPresent()) {
            Gestionnaire gestionnaire = existingGestionnaire.get();

            gestionnaire.setNom(updatedGestionnaire.getNom());
            gestionnaire.setPrenom(updatedGestionnaire.getPrenom());
            gestionnaire.setAdresseCourriel(updatedGestionnaire.getAdresseCourriel());
            gestionnaire.setMotDePasse(updatedGestionnaire.getMotDePasse());

            return gestionnaireRepository.save(gestionnaire);
        } else {
            throw new IllegalArgumentException("Gestionnaire with ID " + id + " does not exist.");
        }
    }

    public void deleteGestionnaire(Long id) {
        gestionnaireRepository.deleteById(id);
    }

}

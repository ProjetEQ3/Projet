package cal.projeteq3.glucose.services;

import cal.projeteq3.glucose.domain.Employeur;
import cal.projeteq3.glucose.domain.Etudiant;
import cal.projeteq3.glucose.repositories.EmployeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeurService {

    private final EmployeurRepository employeurRepository;

    @Autowired
    public EmployeurService(EmployeurRepository employeurRepository) {
        this.employeurRepository = employeurRepository;
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

}

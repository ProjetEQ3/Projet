package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.Etudiant;
import cal.projeteq3.glucose.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Autowired
    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    // database operations here

    public Etudiant createEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Optional<Etudiant> getEtudiantByID(Long id) {
        return etudiantRepository.findById(id);
    }

    public Etudiant updateEtudiant(Long id, Etudiant updatedEtudiant) {
        Optional<Etudiant> existingEtudiant = etudiantRepository.findById(id);
        if(existingEtudiant.isPresent()) {
            Etudiant etudiant = existingEtudiant.get();

            etudiant.setNom(updatedEtudiant.getNom());
            etudiant.setPrenom(updatedEtudiant.getPrenom());
            etudiant.setAdresseCourriel(updatedEtudiant.getAdresseCourriel());
            etudiant.setMotDePasse(updatedEtudiant.getMotDePasse());
            etudiant.setMatricule(updatedEtudiant.getMatricule());
            etudiant.setDepartement(updatedEtudiant.getDepartement());

            return etudiantRepository.save(etudiant);
        } else {
            throw new IllegalArgumentException("Etudiant with ID " + id + " does not exist.");
        }
    }

    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

}

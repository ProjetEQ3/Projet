package cal.projeteq3.glucose.services;

import cal.projeteq3.glucose.domain.Etudiant;
import cal.projeteq3.glucose.repositories.EtudiantRepository;
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
            etudiant.setCodeEtudiant(updatedEtudiant.getCodeEtudiant());
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

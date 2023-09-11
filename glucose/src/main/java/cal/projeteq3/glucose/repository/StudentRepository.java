package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // query methods here

}

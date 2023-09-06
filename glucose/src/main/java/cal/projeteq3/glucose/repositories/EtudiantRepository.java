package cal.projeteq3.glucose.repositories;

import cal.projeteq3.glucose.domain.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // query methods here

}

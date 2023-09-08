package cal.projeteq3.glucose.repositories;

import cal.projeteq3.glucose.domain.Gestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestionnaireRepository extends JpaRepository<Gestionnaire, Long> {

    // query methods here

}

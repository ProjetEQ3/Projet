package cal.projeteq3.glucose.repositories;

import cal.projeteq3.glucose.domain.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long> {

    Utilisateur findUtilisateurByAdresseCourriel(String adresseCourriel);



}

package cal.projeteq3.glucose.services;

import cal.projeteq3.glucose.domain.Utilisateur;
import cal.projeteq3.glucose.repositories.EmployeurRepository;
import cal.projeteq3.glucose.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class myService {

    private final UtilisateurRepo utilisateurRepo;

    @Autowired
    public myService(UtilisateurRepo utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
    }

    public Utilisateur findUtilisateur(String adresseCourriel){
        return utilisateurRepo.findUtilisateurByAdresseCourriel(adresseCourriel);
    }

}

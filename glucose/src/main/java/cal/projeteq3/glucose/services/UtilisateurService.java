package cal.projeteq3.glucose.services;

import cal.projeteq3.glucose.Dto.auth.LoginDto;
import cal.projeteq3.glucose.domain.Utilisateur;
//import cal.projeteq3.glucose.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtilisateurService {
	public String authenticateUser(LoginDto loginDto){
		//to implement this temporary in a way that it will work correctly
		//if user and pass is correct, then
		//return any String like "token" or "something",
		//we will implement real token later
		throw new UnsupportedOperationException("Not implemented");
	}

//    private final UtilisateurRepo utilisateurRepo;
//
//    @Autowired
//    public UtilisateurService(UtilisateurRepo utilisateurRepo) {
//        this.utilisateurRepo = utilisateurRepo;
//    }
//
//    public Utilisateur findUtilisateur(String adresseCourriel){
//        return utilisateurRepo.findUtilisateurByAdresseCourriel(adresseCourriel);
//    }
}

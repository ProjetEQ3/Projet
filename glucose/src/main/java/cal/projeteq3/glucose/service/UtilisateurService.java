package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.auth.LoginDto;
//import cal.projeteq3.glucose.repositories.UtilisateurRepo;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
	public String authenticateUser(LoginDto loginDto){
		//to implement this temporary in a way that it will work correctly
		//if user and pass is correct, then
		//return user.email like string
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

package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.User;
import cal.projeteq3.glucose.dto.LoginDTO;
//import cal.projeteq3.glucose.repositories.UtilisateurRepo;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService{
	public User authenticateUser(LoginDTO loginDto){
		throw new UnsupportedOperationException("Not implemented");
	}

	//    private final UtilisateurRepo utilisateurRepo;
	//
	//    @Autowired
	//    public UtilisateurService(UtilisateurRepo utilisateurRepo) {
	//        this.utilisateurRepo = utilisateurRepo;
	//    }
	//
	//    public User findUtilisateur(String adresseCourriel){
	//        return utilisateurRepo.findUtilisateurByAdresseCourriel(adresseCourriel);
	//    }
}

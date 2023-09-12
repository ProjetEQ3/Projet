package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.UserDTO;
import cal.projeteq3.glucose.dto.LoginDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	//TODO: authenticateUser: should return UserDTO, not User
	public UserDTO authenticateUser(LoginDTO loginDto){
		throw new UnsupportedOperationException("Not implemented");
	}

	//    private final UtilisateurRepo utilisateurRepo;
	//
	//    @Autowired
	//    public UserService(UtilisateurRepo utilisateurRepo) {
	//        this.utilisateurRepo = utilisateurRepo;
	//    }
	//
	//    public User findUtilisateur(String adresseCourriel){
	//        return utilisateurRepo.findUtilisateurByAdresseCourriel(adresseCourriel);
	//    }
}

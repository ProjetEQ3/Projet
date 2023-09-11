package cal.projeteq3.glucose.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "localhost:5432/projet")
@RestController
@RequestMapping("/utilisateur")
public class UserController {
//    UserService utilisateurService;
//
//    @Autowired
//    public UserController(UserService utilisateurService) {
//        this.utilisateurService = utilisateurService;
//    }
//
//    @GetMapping("/login")
//    public ResponseEntity<User> login(String addresseCourriel, String motDePasse){
//
//        User user = utilisateurService.findUtilisateur(addresseCourriel);
//
//        if (user != null && user.getMotDePasse().equals(motDePasse)){
//            return ResponseEntity.accepted().body(user);
//        }
//        return ResponseEntity.badRequest().build();
//    }

}

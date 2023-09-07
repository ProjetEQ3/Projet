package cal.projeteq3.glucose.controllers;


import cal.projeteq3.glucose.domain.Utilisateur;
import cal.projeteq3.glucose.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "localhost:5432/projet")
@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
//    UtilisateurService utilisateurService;
//
//    @Autowired
//    public UtilisateurController(UtilisateurService utilisateurService) {
//        this.utilisateurService = utilisateurService;
//    }
//
//    @GetMapping("/login")
//    public ResponseEntity<Utilisateur> login(String addresseCourriel, String motDePasse){
//
//        Utilisateur user = utilisateurService.findUtilisateur(addresseCourriel);
//
//        if (user != null && user.getMotDePasse().equals(motDePasse)){
//            return ResponseEntity.accepted().body(user);
//        }
//        return ResponseEntity.badRequest().build();
//    }

}

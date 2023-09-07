package cal.projeteq3.glucose.controllers;


import cal.projeteq3.glucose.domain.Utilisateur;
import cal.projeteq3.glucose.services.myService;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "localhost:5432/projet")

@RestController
public class UtilisateurController {
    Utilisateur utilisateur;

    @Autowired
    myService myService;

    @GetMapping("/login")
    public boolean login(String addresseCourriel, String motDePasse){
        utilisateur = myService.findUtilisateur(addresseCourriel);
        return utilisateur.getMotDePasse().equals(motDePasse);
        //Faire rediriger?
    }



}

package cal.projeteq3.glucose.controllers;

import cal.projeteq3.glucose.domain.Etudiant;
import cal.projeteq3.glucose.services.EtudiantService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@RestController
@RequestMapping("/etudiant")
public class EtudiantController {
    EtudiantService etudiantService;

    @Autowired
    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }


    @PostMapping("/register")
    public ResponseEntity<Etudiant> register(@RequestBody Etudiant etudiant){
        return ResponseEntity.accepted().body(etudiantService.createEtudiant(etudiant));
    }

}

/*

Nom et prénom

Adresse courriel (Unique)

Code d'étudiant (Unique)

Mot de passe

Confirmation Mot de passe

Département d'étude (dropdown)
 */
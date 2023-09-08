package cal.projeteq3.glucose.controllers;

import cal.projeteq3.glucose.domain.Etudiant;
import cal.projeteq3.glucose.domain.dto.EtudiantDTO;
import cal.projeteq3.glucose.services.EtudiantService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/etudiant")
public class EtudiantController {
    EtudiantService etudiantService;

    @Autowired
    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<EtudiantDTO> register(@RequestBody Etudiant etudiant){
        etudiantService.createEtudiant(etudiant);
//        return ResponseEntity.accepted().body(etudiantService.createEtudiant(etudiant));
        return null;
    }

}
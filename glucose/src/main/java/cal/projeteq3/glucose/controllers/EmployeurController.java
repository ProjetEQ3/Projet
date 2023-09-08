package cal.projeteq3.glucose.controllers;

import cal.projeteq3.glucose.domain.Employeur;
import cal.projeteq3.glucose.domain.dto.EmployeurDTO;
import cal.projeteq3.glucose.repositories.EmployeurRepository;
import cal.projeteq3.glucose.services.EmployeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employeur")
public class EmployeurController {

    private final EmployeurService empService;

    @Autowired
    public EmployeurController(EmployeurService empService) {
        this.empService = empService;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeurDTO> addEmployeur(@RequestBody Employeur employeur) {
         this.empService.createEmployeur(employeur);
//        return ResponseEntity.accepted().body(this.empService.createEmployeur(employeur));
        return null;
    }

}

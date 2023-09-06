package cal.projeteq3.glucose.controllers;

import cal.projeteq3.glucose.domain.Employeur;
import cal.projeteq3.glucose.repositories.EmployeurRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employeur")
public class EmployeurController {

    private final EmployeurRepository employeurRepository;

    public EmployeurController(EmployeurRepository employeurRepository) {
        this.employeurRepository = employeurRepository;
    }

    @PostMapping("/register")
    public Employeur addEmployeur(@RequestBody Employeur employeur) {
        return this.employeurRepository.save(employeur);
    }

}

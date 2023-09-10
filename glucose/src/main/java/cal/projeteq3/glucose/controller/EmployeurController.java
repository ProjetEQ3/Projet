package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.model.JobOffer;
import cal.projeteq3.glucose.model.Employeur;
import cal.projeteq3.glucose.dto.EmployeurDTO;
import cal.projeteq3.glucose.service.EmployeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/offre/all")
    public ResponseEntity<Iterable<JobOffer>> getAllJobOffers(){
        return ResponseEntity.ok(this.empService.getAllJobOffers());
    }

    @PostMapping("/offre")
    public ResponseEntity<JobOffer> addJobOffer(@RequestBody JobOffer JobOffer){
        return ResponseEntity.accepted().body(this.empService.createJobOffer(JobOffer));
    }

    @PutMapping("/offre/{id}")
    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable Long id, @RequestBody JobOffer JobOffer){
        return ResponseEntity.accepted().body(this.empService.updateJobOffer(id, JobOffer));
    }

    @DeleteMapping("/offre/{id}")
    public ResponseEntity<JobOffer> deleteJobOffer(@PathVariable Long id){
        this.empService.deleteJobOffer(id);
        return ResponseEntity.accepted().build();
    }

}

package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.model.JobOffer;
import cal.projeteq3.glucose.model.Employer;
import cal.projeteq3.glucose.dto.EmployerDTO;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    private final EmployerService empService;

    @Autowired
    public EmployerController(EmployerService empService) {
        this.empService = empService;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployerDTO> addEmployeur(@RequestBody Employer employer) {
        try {
            Validation.validateEmploye(employer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
         this.empService.createEmployeur(employer);
//        return ResponseEntity.accepted().body(this.empService.createEmployeur(employer));
        return null;
    }

    @GetMapping("/offer/all")
    public ResponseEntity<List<JobOffer>> getAllJobOffers(){
        return ResponseEntity.ok(this.empService.getAllJobOffers());
    }

    @PostMapping("/offer")
    public ResponseEntity<JobOffer> addJobOffer(@RequestBody JobOffer JobOffer){
        return ResponseEntity.accepted().body(this.empService.createJobOffer(JobOffer));
    }

    @PutMapping("/offer/{id}")
    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable Long id, @RequestBody JobOffer JobOffer){
        return ResponseEntity.accepted().body(this.empService.updateJobOffer(id, JobOffer));
    }

    @DeleteMapping("/offer/{id}")
    public ResponseEntity<JobOffer> deleteJobOffer(@PathVariable Long id){
        this.empService.deleteJobOffer(id);
        return ResponseEntity.accepted().build();
    }

}

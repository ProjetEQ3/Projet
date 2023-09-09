package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOffreDTO;
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
    public ResponseEntity<Iterable<JobOffreDTO>> getAllJobOffres(){
        return ResponseEntity.ok(this.empService.getAllJobOffres());
    }

    @PostMapping("/offre")
    public ResponseEntity<JobOffreDTO> addJobOffre(@RequestBody JobOffreDTO jobOffreDTO){
        return ResponseEntity.accepted().body(this.empService.createJobOffre(jobOffreDTO));
    }

    @PutMapping("/offre/{id}")
    public ResponseEntity<JobOffreDTO> updateJobOffre(@PathVariable Long id, @RequestBody JobOffreDTO jobOffreDTO){
        return ResponseEntity.accepted().body(this.empService.updateJobOffre(id, jobOffreDTO));
    }

    @DeleteMapping("/offre/{id}")
    public ResponseEntity<JobOffreDTO> deleteJobOffre(@PathVariable Long id){
        this.empService.deleteJobOffre(id);
        return ResponseEntity.accepted().build();
    }

}

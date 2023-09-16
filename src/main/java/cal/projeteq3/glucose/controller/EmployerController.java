package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.model.JobOffer;
import cal.projeteq3.glucose.model.Employer;
import cal.projeteq3.glucose.dto.EmployerDTO;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployerController {

    private final EmployerService empService;

    public EmployerController(EmployerService empService) {
        this.empService = empService;
    }

    /*@PostMapping("/register")
    //TODO DTO PLZ
    public ResponseEntity<EmployerDTO> register(@RequestBody Employer employer) {

        try {
            Validation.validateEmploye(employer);
            return ResponseEntity.status(HttpStatus.CREATED).body(this.empService.createEmployer(employer));

        } catch (ValidationException e) {
            return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", e.getMessage()).body(null);
        }
    }*/

    @GetMapping("/offer/all")
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffers(){
        return ResponseEntity.ok(this.empService.getAllJobOffers());
    }

    @PostMapping("/offer")
    public ResponseEntity<JobOfferDTO> addJobOffer(@RequestBody JobOfferDTO JobOffer){
        try{
            return ResponseEntity.accepted().body(this.empService.createJobOffer(JobOffer));
        }catch(ValidationException e){
            return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", "Invalid Operation").body(null);
        }
    }

    @PutMapping("/offer/{id}")
    public ResponseEntity<JobOfferDTO> updateJobOffer(@PathVariable Long id, @RequestBody JobOfferDTO JobOffer){
        try{
            return ResponseEntity.accepted().body(this.empService.updateJobOffer(id, JobOffer));
        }catch(ValidationException e){
            return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", "Invalid operation").body(null);
        }
    }

    @DeleteMapping("/offer/{id}")
    public ResponseEntity<JobOfferDTO> deleteJobOffer(@PathVariable Long id){
        this.empService.deleteJobOffer(id);
        return ResponseEntity.accepted().build();
    }

}

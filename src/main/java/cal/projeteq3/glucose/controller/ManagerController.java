package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = "http://localhost:3000")
public class ManagerController {

    private final ManagerService managerService;
    private final EmployerService employerService;

    @Autowired
    public ManagerController(ManagerService managerService, EmployerService employerService) {
        this.managerService = managerService;
        this.employerService = employerService;
    }

    @GetMapping("/jobOffers/all")
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffer(){
        return ResponseEntity.ok(managerService.getAllJobOffer());
    }

    @GetMapping("jobOffer/{id}")
    public ResponseEntity<JobOfferDTO> getJobOfferById(@PathVariable Long id){
        return ResponseEntity.ok(managerService.getJobOfferByID(id));
    }

    @GetMapping("jobOffers/{employerId}")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferByEmploye(@PathVariable Long employerId){
        System.out.println("ID: " + employerId);
        return ResponseEntity.ok(employerService.getJobOffersDTOByEmployerId(employerId));
    }

    @GetMapping("jobOffers/submitted")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferSubmitted(){
        return ResponseEntity.ok(managerService.getJobOffersWithState(JobOfferState.SUBMITTED));
    }

    @GetMapping("jobOffers/open")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferOpen(){
        return ResponseEntity.ok(managerService.getJobOffersWithState(JobOfferState.OPEN));
    }

    @GetMapping("jobOffers/refused")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferRefused(){
        return ResponseEntity.ok(managerService.getJobOffersWithState(JobOfferState.REFUSED));
    }

    @GetMapping("jobOffers/taken")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferTaken(){
        return ResponseEntity.ok(managerService.getJobOffersWithState(JobOfferState.TAKEN));
    }
}

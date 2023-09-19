package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.model.cvFile.CvState;
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

//    JobOffer

    @GetMapping("/jobOffers/all")
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffer(){
        return ResponseEntity.ok(managerService.getAllJobOffer());
    }

    @GetMapping("jobOffer/{id}")
    public ResponseEntity<JobOfferDTO> getJobOfferById(@PathVariable Long id){
        return ResponseEntity.ok(managerService.getJobOfferByID(id));
    }

    @GetMapping("jobOffers/employer/{employerId}")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferByEmploye(@PathVariable Long employerId){
        System.out.println("ID: " + employerId);
        return ResponseEntity.ok(employerService.getJobOffersDTOByEmployerId(employerId));
    }

    @GetMapping("jobOffers/{jobOfferState}")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferByState(@PathVariable String jobOfferState){
        return ResponseEntity.ok(managerService.getJobOffersWithState(JobOfferState.valueOf(jobOfferState.toUpperCase())));
    }

    @PutMapping("jobOffer/accept/{id}")
    public ResponseEntity<JobOfferDTO> updateJobOfferState(@PathVariable Long id){
        return ResponseEntity.ok(managerService.updateJobOfferState(id, JobOfferState.valueOf("OPEN"), null));
    }

    @PutMapping("jobOffer/refuse/{id}")
    public ResponseEntity<JobOfferDTO> updateJobOfferState(@PathVariable Long id, @RequestBody String reason){
        return ResponseEntity.ok(managerService.updateJobOfferState(id, JobOfferState.valueOf("REFUSED"), reason));
    }

//    CV

//TODO: Utiliser la fonction updateCvState(Long id, CvState newState, String reason)
// (String reason va venir si ce n'est pas déjà fait)
//    Puisque c'est un update, il faut utiliser PUT et non POST

    @PostMapping("/cv/accepted/{id}")
    public ResponseEntity<?> acceptCv(@PathVariable Long id){
        managerService.acceptCv(id);
        return ResponseEntity.ok().build();
    }

//TODO: Utiliser la fonction updateCvState(Long id, CvState newState, String reason)
// (String reason va venir si ce n'est pas déjà fait)
//    Puisque c'est un update, il faut utiliser PUT et non POST
    @PostMapping("/cv/refused/{id}")
    public ResponseEntity<?> refuseCv(@PathVariable Long id){
        managerService.refuseCv(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("cvs/all")
    public ResponseEntity<List<CvFileDTO>> getAllCV(){
        return ResponseEntity.ok(managerService.getAllCv());
    }

    @GetMapping("cvs/pending")
    public ResponseEntity<List<CvFileDTO>> getCVByState(){
        return ResponseEntity.ok(managerService.getSubmittedCv());
    }

}

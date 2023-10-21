package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
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
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffer(@RequestParam String season, @RequestParam String year){
        Semester semester = new Semester(Semester.Season.valueOf(season), Integer.parseInt(year));

        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getAllJobOffer(semester));
    }

    @GetMapping("jobOffer/{id}")
    public ResponseEntity<JobOfferDTO> getJobOfferById(@PathVariable Long id){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getJobOfferByID(id));
    }

    @GetMapping("jobOffers/employer/{employerId}")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferByEmployer(@PathVariable Long employerId, @RequestParam String season, @RequestParam String year){
        Semester semester = new Semester(Semester.Season.valueOf(season), Integer.parseInt(year));

        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(employerService.getJobOffersDTOByEmployerId(employerId, semester));
    }

    @GetMapping("jobOffers/{jobOfferState}")
    public ResponseEntity<List<JobOfferDTO>> getJobOfferByState(@PathVariable String jobOfferState, @RequestParam String season, @RequestParam String year){
        Semester semester = new Semester(Semester.Season.valueOf(season), Integer.parseInt(year));

        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getJobOffersWithState(JobOfferState.valueOf(jobOfferState.toUpperCase()), semester));
    }

    @PutMapping("jobOffer/accept/{id}")
    public ResponseEntity<JobOfferDTO> updateJobOfferState(@PathVariable Long id){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.updateJobOfferState(id, JobOfferState.valueOf("OPEN"), null));
    }

    @PutMapping("jobOffer/refuse/{id}")
    public ResponseEntity<JobOfferDTO> updateJobOfferState(@PathVariable Long id, @RequestBody String reason){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.updateJobOfferState(id, JobOfferState.valueOf("REFUSED"), reason));
    }

    @PutMapping("/cv/update/{id}")
    public ResponseEntity<CvFileDTO> updateCvState(@PathVariable Long id, @RequestParam String newCvState, @RequestParam String reason){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.updateCvState(id, CvState.valueOf(newCvState), reason));
    }

    @GetMapping("cvs/all")
    public ResponseEntity<List<CvFileDTO>> getAllCV(){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getAllCv());
    }

    @GetMapping("cvs/pending")
    public ResponseEntity<List<CvFileDTO>> getCvsSubmitted(){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getSubmittedCv());
    }

}

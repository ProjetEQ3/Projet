package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import cal.projeteq3.glucose.service.UserService;
import cal.projeteq3.glucose.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final EmployerService employerService;
    private final UserService userService;

//    JobOffer
    @GetMapping("/jobOffers/all")
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffer(@RequestParam String season, @RequestParam String year,
                                                            @RequestParam String department){
        Semester semester = new Semester(Semester.Season.valueOf(season), Integer.parseInt(year));

        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getAllJobOffer(semester, Department.valueOf(department)));
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
    public ResponseEntity<List<JobOfferDTO>> getJobOfferByState(@PathVariable String jobOfferState,
                                                                @RequestParam String season, @RequestParam String year,
                                                                @RequestParam String department){
        Semester semester = new Semester(Semester.Season.valueOf(season), Integer.parseInt(year));

        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getJobOffersWithState(
                        JobOfferState.valueOf(jobOfferState.toUpperCase()), semester, Department.valueOf(department)));
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
    public ResponseEntity<List<CvFileDTO>> getAllCV(@RequestParam String department){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getAllCv(Department.valueOf(department)));
    }

    @GetMapping("cvs/pending")
    public ResponseEntity<List<CvFileDTO>> getCvsSubmitted(@RequestParam String department){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getSubmittedCv(Department.valueOf(department)));
    }

    @GetMapping("/contracts")
    public ResponseEntity<List<ContractDTO>> getAllShortContracts(@RequestParam String season, @RequestParam String year,
                                                                  @RequestParam String department){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getContractsBySession(new Semester(Semester.Season.valueOf(season),
                        Integer.parseInt(year)), Department.valueOf(department)));
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable Long contractId){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getShortContractById(contractId));
    }

    @PostMapping("/contract/sign/{contractId}")
    public ResponseEntity<ContractDTO> signContract(@RequestBody LoginDTO loginDTO, @PathVariable Long contractId){
        Validation.validateLogin(loginDTO);
        long studentId = userService.authenticateUserContractSigning(loginDTO);
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.managerService.signContract(contractId, studentId));
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getStudents(@RequestParam String department){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getStudents(Department.valueOf(department)));
    }

    @GetMapping("/student/jobApplications/{id}")
    public ResponseEntity<List<JobApplicationDTO>> getStudent(@PathVariable Long id){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(managerService.getJobApplicationsByStudentId(id));
    }
}

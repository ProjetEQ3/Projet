package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.service.UserService;
import cal.projeteq3.glucose.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<StudentDTO> register(@RequestBody RegisterStudentDTO student) {
        Validation.validateStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.createStudent(student));
    }

    @GetMapping("/cv/{studentId}")
    public ResponseEntity<CvFileDTO> getCv(@PathVariable Long studentId) {
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getCv(studentId));
    }

    @PostMapping("/cv/{studentId}")
    public ResponseEntity<CvFileDTO> addCv(
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file.isEmpty()) return ResponseEntity.badRequest().build();

        Validation.validateCvFileName(file.getOriginalFilename());

        byte[] fileData = file.getBytes();
        CvFileDTO cvFileDTO = new CvFileDTO();
        cvFileDTO.setFileName(file.getOriginalFilename());
        cvFileDTO.setCvState(CvState.SUBMITTED);
        cvFileDTO.setFileData(fileData);
        CvFileDTO cvFileRes = studentService.addCv(studentId, cvFileDTO);

        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON).body(cvFileRes);
    }

    @DeleteMapping("/cv/{studentId}")
    public ResponseEntity<Void> deleteCv(@PathVariable Long studentId) {
        studentService.deleteCv(studentId);
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON).build();
    }

    @GetMapping("/jobOffers/{department}")
    public ResponseEntity<List<JobOfferDTO>> getJobOffersByDepartment(@PathVariable String department, @RequestParam String season, @RequestParam String year){
        Semester semester = new Semester(Semester.Season.valueOf(season), Integer.parseInt(year));

        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getJobOffersByDepartment(Department.valueOf(department), semester));
    }

    @GetMapping("/jobOffers/open")
    public ResponseEntity<List<JobOfferDTO>> getOpenJobOffersByDepartment(@RequestParam String department, @RequestParam String season, @RequestParam String year){
        Semester semester = new Semester(Semester.Season.valueOf(season), Integer.parseInt(year));

        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getOpenJobOffersByDepartment(Department.valueOf(department), semester));
    }

    @PostMapping("/applyJobOffer/{studentId}/{jobOfferId}")
    public ResponseEntity<JobOfferDTO> applyJobOffer(@PathVariable Long studentId, @PathVariable Long jobOfferId, @RequestBody String coverLetter){
        if (coverLetter == null || coverLetter.isEmpty() || coverLetter.equals("EMPTY_COVER_LETTER")) coverLetter = "";
        return ResponseEntity.accepted().body(studentService.applyJobOffer(jobOfferId, studentId, coverLetter));
    }

    @GetMapping("/appliedJobOffer/{studentId}")
    public ResponseEntity<List<JobOfferDTO>> getAppliedJobOfferByStudentId(@PathVariable Long studentId, @RequestParam String season, @RequestParam String year){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getAppliedJobOfferByStudentId(studentId, Semester.builder()
                        .season(Semester.Season.valueOf(season))
                        .year(Integer.parseInt(year))
                        .build()));
    }

    @GetMapping("/appointmentsByJobApplicationId/{id}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByJobApplicationId(@PathVariable Long id) {
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getAppointmentsByJobApplicationId(id));
    }

    @GetMapping("/appointmentsByJobOfferIdAndStudentId/{jobOfferId}/{studentId}")
    public ResponseEntity<List<AppointmentDTO>> findAllAppointmentsForJobOfferAndStudent(@PathVariable Long jobOfferId, @PathVariable Long studentId) {
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.findAllAppointmentsForJobOfferAndStudent(jobOfferId, studentId));
    }

    @PutMapping("/setAppointmentToChosen/{id}")
    public ResponseEntity<AppointmentDTO> setAppointmentToChosen(@PathVariable Long id) {
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.setAppointmentToChosen(id));
    }

    @GetMapping("/contracts/{studentId}")
    public ResponseEntity<List<ContractDTO>> getContractsByStudentId(@PathVariable Long studentId, @RequestParam String season, @RequestParam String year){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getContractsByStudentId(studentId, Semester.builder()
                        .season(Semester.Season.valueOf(season))
                        .year(Integer.parseInt(year))
                        .build()));
    }

    @PostMapping("/contract/sign/{contractId}")
    public ResponseEntity<ContractDTO> signContract(@RequestBody LoginDTO loginDTO, @PathVariable Long contractId){
        Validation.validateLogin(loginDTO);
        long studentId = userService.authenticateUserContractSigning(loginDTO);
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.studentService.signContract(contractId, studentId));
    }

    @GetMapping("/viewedJobOffers/{studentId}")
    public ResponseEntity<List<Long>> getViewedJobOffersByStudentId(@PathVariable Long studentId){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getViewedJobOffersByStudentId(studentId));
    }


    @PutMapping("/jobOffer/view/{studentId}/{jobOfferId}")
    public ResponseEntity<JobOfferDTO> viewJobOffer(@PathVariable Long studentId, @PathVariable Long jobOfferId) {
        return ResponseEntity.accepted().body(studentService.markJobOfferAsViewed(studentId, jobOfferId));
    }
}

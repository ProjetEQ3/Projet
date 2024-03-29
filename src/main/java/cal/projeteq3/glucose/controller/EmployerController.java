package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.evaluation.InternEvaluationDTO;
import cal.projeteq3.glucose.dto.evaluation.SectionDTO;
import cal.projeteq3.glucose.dto.evaluation.TimeSheetDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.evaluation.timeSheet.TimeSheet;
import cal.projeteq3.glucose.model.evaluation.timeSheet.WeeklyHours;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.UserService;
import cal.projeteq3.glucose.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employer")
public class EmployerController{
	private final EmployerService employerService;
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<EmployerDTO> register(@RequestBody RegisterEmployerDTO employerDTO){
		Validation.validateEmployer(employerDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.createEmployer(employerDTO));
	}

	@GetMapping("/offer/all")
	public ResponseEntity<List<JobOfferDTO>> getAllJobOffers(@RequestParam Long employerId, @RequestParam String season, @RequestParam String year){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getAllJobOffers(employerId, getSemesterFrom(season, year)));
	}

	@PostMapping("/offer")
	public ResponseEntity<JobOfferDTO> addJobOffer(@RequestBody JobOfferDTO JobOffer, @RequestParam Long employerId){
		Validation.validateJobOffer(JobOffer);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.createJobOffer(JobOffer, employerId));
	}

	@PutMapping("/offer")
	public ResponseEntity<JobOfferDTO> updateJobOffer(@RequestBody JobOfferDTO JobOffer){
		Validation.validateJobOffer(JobOffer);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.updateJobOffer(JobOffer));
	}

	@DeleteMapping("/offer/{id}")
	public ResponseEntity<?> deleteJobOffer(@PathVariable Long id){
		this.employerService.deleteJobOffer(id);
		return ResponseEntity.accepted()
				.build();
	}

	@PutMapping("/offer/accept/{jobApplicationId}")
	public ResponseEntity<JobApplicationDTO> acceptJobApplication(@PathVariable Long jobApplicationId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.acceptApplication(jobApplicationId));
	}

	@PutMapping("/offer/refuse/{jobApplicationId}")
	public ResponseEntity<JobApplicationDTO> refuseJobApplication(@PathVariable Long jobApplicationId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.refuseApplication(jobApplicationId));
	}

	@GetMapping("/offer/applications/{id}")
	public ResponseEntity<List<JobApplicationDTO>> getApplicationsByJobOfferId(@PathVariable Long id){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getPendingApplicationsByJobOfferId(id));
	}

	@PutMapping("/offer/appointment/{applicationId}")
	public ResponseEntity<JobApplicationDTO> addSuggestedAppointment(@PathVariable Long applicationId, @RequestBody Set<LocalDateTime> dates){
		Validation.validateAppointmentDate(dates);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.addAppointmentByJobApplicationId(applicationId, dates));
	}

	@GetMapping("/offer/appointment/{applicationId}")
	public ResponseEntity<AppointmentDTO> getSuggestedAppointment(@PathVariable Long applicationId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getAppointmentByJobApplicationId(applicationId));
	}

	@GetMapping("/waitingStudents")
	public ResponseEntity<List<StudentDTO>> getWaitingStudents(@RequestParam Long employerId, @RequestParam String season, @RequestParam String year){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getWaitingStudents(employerId, getSemesterFrom(season, year)));
	}

	@GetMapping("/offerByApplication")
	public ResponseEntity<JobOfferDTO> getOfferByApplicationId(@RequestParam Long applicationId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getOfferByApplicationId(applicationId));
	}

	@GetMapping("/contracts/{employerId}")
	public ResponseEntity<List<ContractDTO>> getAllContracts(@PathVariable Long employerId, @RequestParam String season, @RequestParam String year){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getContractsBySession(getSemesterFrom(season, year), employerId));
	}

	@PostMapping("/contract/sign/{contractId}")
	public ResponseEntity<ContractDTO> signContract(@RequestBody LoginDTO loginDTO, @PathVariable Long contractId){
		Validation.validateLogin(loginDTO);
		long studentId = userService.authenticateUserContractSigning(loginDTO);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.signContract(contractId, studentId));
	}

	private Semester getSemesterFrom(String season, String year){
		return Semester.builder()
				.season(Semester.Season.valueOf(season))
				.year(Integer.parseInt(year))
				.build();
	}

	@GetMapping("/nbApplications/{employerId}")
	public ResponseEntity<?> nbSubmittedApplicationsAcrossAllJobOffersFromEmployer(@PathVariable Long employerId) {
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.nbSubmittedApplicationsAcrossAllJobOffersFromEmployer(employerId));
	}

	@GetMapping("/offer/submittedApplications/{employerId}")
	public ResponseEntity<List<JobOfferDTO>> getAllJobOffersWithSubmittedApplicationsFromEmployer(@PathVariable Long employerId) {
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getAllJobOffersWithSubmittedApplicationsFromEmployer(employerId));
	}

	@GetMapping("/applications/{employerId}")
	public ResponseEntity<List<JobApplicationDTO>> getAllAcceptedJobApplicationsByEmployerId(@PathVariable Long employerId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getAllAcceptedJobApplicationsByEmployerId(employerId));
	}

	@GetMapping("/timeSheet/{jobApplicationId}")
	public ResponseEntity<TimeSheetDTO> getTimeSheet(@PathVariable Long jobApplicationId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getTimeSheetByJobApplicationId(jobApplicationId));
	}

	@PostMapping("/timeSheet/{jobApplicationId}")
	public ResponseEntity<JobApplicationDTO> saveTimeSheet(@PathVariable Long jobApplicationId, @RequestBody List<WeeklyHours> weeklyHours){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.saveTimeSheetForJobApplicationId(jobApplicationId, weeklyHours));
	}

	@PostMapping("/internEvaluation/{jobApplicationId}")
	public ResponseEntity<InternEvaluationDTO> saveStudentEvaluation(@PathVariable Long jobApplicationId, @RequestBody List<SectionDTO> sections){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.saveInternEvaluationForJobApplicationId(jobApplicationId, sections));
	}
}

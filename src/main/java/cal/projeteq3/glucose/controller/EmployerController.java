package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employer")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployerController{
	private final EmployerService employerService;

	@Autowired
	public EmployerController(EmployerService employerService){
		this.employerService = employerService;
	}

	@PostMapping("/register")
	public ResponseEntity<EmployerDTO> register(@RequestBody RegisterEmployerDTO employerDTO){
		Validation.validateEmployer(employerDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.createEmployer(employerDTO));
	}

	@GetMapping("/offer/all")
	public ResponseEntity<List<JobOfferDTO>> getAllJobOffers(@RequestParam Long employerId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getAllJobOffers(employerId));
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
		this.employerService.acceptApplication(jobApplicationId);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.build();
	}

	@PutMapping("/offer/refuse/{jobApplicationId}")
	public ResponseEntity<JobApplicationDTO> refuseJobApplication(@PathVariable Long jobApplicationId){
		this.employerService.refuseApplication(jobApplicationId);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.build();
	}

	@GetMapping("/offer/{id}/students")
	public ResponseEntity<List<StudentDTO>> getStudentsByJobOffer(@PathVariable Long id){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.employerService.getStudentsByJobOfferId(id));
	}

}

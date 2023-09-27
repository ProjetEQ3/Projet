package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employer")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployerController{
	private final EmployerService empService;

	public EmployerController(EmployerService empService){
		this.empService = empService;
	}

	@PostMapping("/register")
	public ResponseEntity<EmployerDTO> register(@RequestBody RegisterEmployerDTO employerDTO){
		Validation.validateEmployer(employerDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.empService.createEmployer(employerDTO));
	}

	@GetMapping("/offer/all")
	public ResponseEntity<List<JobOfferDTO>> getAllJobOffers(@RequestParam Long employerId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.empService.getAllJobOffers(employerId));
	}

	@PostMapping("/offer")
	public ResponseEntity<JobOfferDTO> addJobOffer(@RequestBody JobOfferDTO JobOffer, @RequestParam Long employerId){
		Validation.validateJobOffer(JobOffer);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.empService.createJobOffer(JobOffer, employerId));
	}

	@PutMapping("/offer")
	public ResponseEntity<JobOfferDTO> updateJobOffer(@RequestBody JobOfferDTO JobOffer){
		Validation.validateJobOffer(JobOffer);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.empService.updateJobOffer(JobOffer));
	}

	@DeleteMapping("/offer/{id}")
	public ResponseEntity<?> deleteJobOffer(@PathVariable Long id){
		this.empService.deleteJobOffer(id);
		return ResponseEntity.accepted()
				.build();
	}

}

package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.http.HttpStatus;
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
		try{
			Validation.validateEmployer(employerDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(this.empService.createEmployer(employerDTO));
		}catch(APIException e){
			return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", e.getMessage()).body(null);
		}
	}

	@GetMapping("/offer/all")
	public ResponseEntity<List<JobOfferDTO>> getAllJobOffers(@RequestParam Long employerId){
		return ResponseEntity.ok(this.empService.getAllJobOffers(employerId));
	}

	@PostMapping("/offer")
	public ResponseEntity<JobOfferDTO> addJobOffer(@RequestBody JobOfferDTO JobOffer, @RequestParam Long employerId){
		try{
			Validation.validateJobOffer(JobOffer);
			return ResponseEntity.accepted().body(this.empService.createJobOffer(JobOffer, employerId));
		}catch(APIException e){
			return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", "Invalid Operation").body(null);
		}
	}

	@PutMapping("/offer")
	public ResponseEntity<JobOfferDTO> updateJobOffer(@RequestBody JobOfferDTO JobOffer){
		try{
			Validation.validateJobOffer(JobOffer);
			return ResponseEntity.accepted().body(this.empService.updateJobOffer(JobOffer));
		}catch(APIException e){
			return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", "Invalid operation").body(null);
		}
	}

	@DeleteMapping("/offer/{id}")
	public ResponseEntity<?> deleteJobOffer(@PathVariable Long id){
		this.empService.deleteJobOffer(id);
		return ResponseEntity.accepted().build();
	}

}

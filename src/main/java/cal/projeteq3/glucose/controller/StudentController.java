package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController{
	private final StudentService studentService;

	public StudentController(StudentService studentService){
		this.studentService = studentService;
	}

	@PostMapping("/register")
	public ResponseEntity<StudentDTO> register(@RequestBody RegisterStudentDTO student){
		try{
			Validation.validateStudent(student);
			return ResponseEntity.status(HttpStatus.CREATED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(studentService.createStudent(student));
		}catch(APIException e){
			return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", e.getMessage()).body(null);
		}
	}

	@PostMapping("/cv/{studentId}")
	public ResponseEntity<CvFileDTO> addCv(
			@PathVariable Long studentId,
			@RequestParam("file") MultipartFile file
	){
		if(file.isEmpty()) return ResponseEntity.badRequest().build();
		try{
			byte[] fileData = file.getBytes();
			CvFileDTO cvFileDTO = new CvFileDTO();
			cvFileDTO.setFileName(file.getOriginalFilename());
			cvFileDTO.setCvState(CvState.SUBMITTED);
			cvFileDTO.setFileData(fileData);
			System.out.println("fileData: " + fileData.length);
			return ResponseEntity.accepted().body(studentService.addCv(studentId, cvFileDTO));
		}catch(APIException e){
			return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
		}catch(Exception e){
			return ResponseEntity.status(400).header("X-Errors", "Invalide operation").body(null);
		}
	}

	@DeleteMapping("/cv/{studentId}")
	public ResponseEntity<Void> deleteCv(@PathVariable Long studentId){
		try{
			studentService.deleteCv(studentId);
			return ResponseEntity.accepted().build();
		}catch(APIException e){
			return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).build();
		}catch(Exception e){
			return ResponseEntity.status(400).header("X-Errors", "Invalide operation").build();
		}
	}

	@GetMapping("/jobOffers/{department}")
	public ResponseEntity<List<JobOfferDTO>> getJobOffersByDepartment(@PathVariable String department){
		try{
			return ResponseEntity.accepted().body(studentService.getJobOffersByDepartment(Department.valueOf(department)));
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", "Invalide operation").body(null);
		}
	}

	@GetMapping("/jobOffers/open/{department}")
	public ResponseEntity<List<JobOfferDTO>> getOpenJobOffersByDepartment(@PathVariable String department){
		try{
			return ResponseEntity.accepted().body(studentService.getOpenJobOffersByDepartment(Department.valueOf(department)));
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", "Invalide operation").body(null);
		}
	}

	// EQ3-13
	@PostMapping("/applyJobOffer/{studentId}/{jobOfferId}")
	public ResponseEntity<JobOfferDTO> applyJobOffer(@PathVariable Long studentId, @PathVariable Long jobOfferId){
		return ResponseEntity.accepted().body(studentService.applyJobOffer(jobOfferId, studentId));

	}

	@GetMapping("/appliedJobOffer/{studentId}")
	public ResponseEntity<List<JobOfferDTO>> getAppliedJobOfferByStudentId(@PathVariable Long studentId){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(studentService.getAppliedJobOfferByStudentId(studentId));
	}

}
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

import java.io.IOException;
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
		Validation.validateStudent(student);
		return ResponseEntity.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(studentService.createStudent(student));
	}

	@PostMapping("/cv/{studentId}")
	public ResponseEntity<CvFileDTO> addCv(
		@PathVariable Long studentId,
		@RequestParam("file") MultipartFile file
	) throws IOException {
		if(file.isEmpty()) return ResponseEntity.badRequest().build();

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
	public ResponseEntity<Void> deleteCv(@PathVariable Long studentId){
		studentService.deleteCv(studentId);
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON).build();
	}

	@GetMapping("/jobOffers/{department}")
	public ResponseEntity<List<JobOfferDTO>> getJobOffersByDepartment(@PathVariable String department){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(studentService.getJobOffersByDepartment(Department.valueOf(department)));
	}

	@GetMapping("/jobOffers/open/{department}")
	public ResponseEntity<List<JobOfferDTO>> getOpenJobOffersByDepartment(@PathVariable String department){
		return ResponseEntity.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(studentService.getOpenJobOffersByDepartment(Department.valueOf(department)));
	}

	// EQ3-13
	@PostMapping("/applyJobOffer/{studentId}/{jobOfferId}")
	public ResponseEntity<JobOfferDTO> applyJobOffer(@PathVariable Long studentId, @PathVariable Long jobOfferId){
		return ResponseEntity.accepted().body(studentService.applyJobOffer(jobOfferId, studentId));
	}

}

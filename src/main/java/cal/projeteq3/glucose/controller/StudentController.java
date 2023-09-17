package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //TODO DTO PLZ
    @PostMapping("/register")
    public ResponseEntity<StudentDTO> register(@RequestBody Student student){
        System.out.println(student);
        try {
            Validation.validateStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));
        } catch (ValidationException e) {
            return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", e.getMessage()).body(null);
        }
    }

    @PostMapping("/cv")
    public ResponseEntity<StudentDTO> addCv(@RequestBody StudentDTO student){
        try {
            return ResponseEntity.accepted().body(studentService.addCv(student.getId(), student.getCvFile()));
        } catch (ValidationException e) {
            return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
        }catch(Exception e){
            return ResponseEntity.status(400).header("X-Errors", "Invalide operation").body(null);
        }
    }

    @DeleteMapping("/cv/{studentId}")
    public ResponseEntity<Void> deleteCv(@PathVariable Long studentId){
        try {
            studentService.deleteCv(studentId);
            return ResponseEntity.ok().build();
        }catch(ValidationException e){
            return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).build();
        }catch (Exception e) {
            return ResponseEntity.status(400).header("X-Errors", "Invalide operation").build();
        }
    }

}

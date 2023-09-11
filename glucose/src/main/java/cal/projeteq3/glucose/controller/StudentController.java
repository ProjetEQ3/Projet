package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.dto.StudentDTO;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/register")
    public ResponseEntity<StudentDTO> register(@RequestBody Student student){
        try {
            Validation.validateStudent(student);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.accepted().body(studentService.createStudent(student));
    }

}
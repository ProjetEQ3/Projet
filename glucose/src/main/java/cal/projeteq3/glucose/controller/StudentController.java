package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.dto.StudentDTO;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/register")
    public ResponseEntity<StudentDTO> register(@RequestBody Student student){
        // TODO: 2023-09-08 add validation & Return StudentDTO
        try {
            Validation.validateStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        studentService.createStudent(student);
//        return ResponseEntity.accepted().body(etudiantService.createEtudiant(student));
        return null;
    }

}
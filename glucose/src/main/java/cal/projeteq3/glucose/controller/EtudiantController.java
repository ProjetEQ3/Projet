package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.dto.StudentDTO;
import cal.projeteq3.glucose.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/etudiant")
public class EtudiantController {
    StudentService studentService;

    @Autowired
    public EtudiantController(StudentService studentService) {
        this.studentService = studentService;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<StudentDTO> register(@RequestBody Student student){
        // TODO: 2023-09-08 add validation & Return StudentDTO
        studentService.createEtudiant(student);
//        return ResponseEntity.accepted().body(etudiantService.createEtudiant(student));
        return null;
    }

}
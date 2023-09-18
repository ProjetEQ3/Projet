package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.service.UserService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController{
    private final UserService userService;
    private final StudentService studentService;
    private final EmployerService employerService;

    public UserController(UserService userService, StudentService studentService, EmployerService employerService){
        this.userService = userService;
        this.studentService = studentService;
        this.employerService = employerService;
    }

//    @PostMapping({"", "/"})
//    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO registerDTO){
//        System.out.println("register:" + registerDTO.getEmail());
//        try {
//            return ResponseEntity
//              .status(HttpStatus.CREATED)
//              .body(this.userService.createUser(registerDTO));
//        } catch (ValidationException e) {
//            System.out.println("ValidationException:" + e.getMessage());
//            return ResponseEntity
//              .status(e.getStatus())
//              .header("X-Errors", e.getMessage())
//              .body(null);
//        } /*catch (Exception e) {
//            System.out.println("Exception:" + e.getMessage());
//            return ResponseEntity
//              .status(HttpStatus.INTERNAL_SERVER_ERROR)
//              .header("X-Errors", e.getMessage())
//              .body(null);
//        }*/
//    }

//    @PostMapping("/register")
//    public ResponseEntity<StudentDTO> register(@RequestBody RegisterDTO registerDTO){
//        try {
//            Validation.validateRegister(registerDTO);
//
//            return switch (registerDTO.getRole()) {
//                case "STUDENT" ->
//                        ResponseEntity.status(HttpStatus.CREATED).body(this.studentService.createStudent(registerDTO));
//                case "EMPLOYER" ->
//                        ResponseEntity.status(HttpStatus.BAD_REQUEST).header("X-Errors", "Invalid role").body(null);
////                        ResponseEntity.status(HttpStatus.CREATED).body(this.employerService.createEmployer(registerDTO));
//                default -> ResponseEntity.status(HttpStatus.BAD_REQUEST).header("X-Errors", "Invalid role").body(null);
//            };
//        } catch (ValidationException e) {
//            return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", e.getMessage()).body(null);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> Login(@RequestBody LoginDTO loginDTO){
        try {
            Validation.validateLogin(loginDTO);
            return ResponseEntity
              .status(HttpStatus.ACCEPTED)
              .body(this.userService.authenticateUser(loginDTO));
        } catch (ValidationException e){
            return ResponseEntity
              .status(e.getStatus())
              .header("X-Errors", e.getMessage())
              .body(null);
        } catch (Exception e) {
            return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .header("X-Errors", e.getMessage())
              .body(null);
        }
    }

}

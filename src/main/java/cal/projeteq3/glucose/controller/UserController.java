package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
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

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO registerDTO){
        System.out.println("register:" + registerDTO.getEmail());
        try {
            return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(this.userService.createUser(registerDTO));
        } catch (ValidationException e) {
            System.out.println("ValidationException:" + e.getMessage());
            return ResponseEntity
              .status(e.getStatus())
              .header("X-Errors", e.getMessage())
              .body(null);
        } /*catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .header("X-Errors", e.getMessage())
              .body(null);
        }*/
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> Login(@RequestBody LoginDTO loginDTO){
        try {
            Validation.validateLogin(loginDTO);
            return ResponseEntity
              .status(HttpStatus.ACCEPTED)
              //TODO DELETE
              .body(null);
              //.body(this.userService.authenticateUser(loginDTO));
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

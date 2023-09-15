package cal.projeteq3.glucose.controller;


import cal.projeteq3.glucose.dto.LoginDTO;
import cal.projeteq3.glucose.dto.UserDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.service.UserService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> Login(@RequestBody LoginDTO loginDTO){

        try {
            Validation.validateLogin(loginDTO);
            System.out.println("LoginDTO is valid");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.userService.authenticateUser(loginDTO));
        } catch (ValidationException e){
            System.out.print("LoginDTO is not valid");
            return ResponseEntity.status(e.getStatus()).header("X-Errors", e.getMessage()).body(null);
        } catch (Exception e) {
            System.out.print("LoginDTO is not valid2");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-Errors", e.getMessage()).body(null);
        }
    }


}

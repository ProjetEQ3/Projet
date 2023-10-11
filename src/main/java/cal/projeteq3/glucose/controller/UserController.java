package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.service.UserService;
import cal.projeteq3.glucose.validation.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController{
	private final UserService userService;

	public UserController(UserService userService){
		this.userService = userService;
	}

    @PostMapping("/login")
    public ResponseEntity<UserDTO> Login(@RequestBody LoginDTO loginDTO){
        Validation.validateLogin(loginDTO);

        return ResponseEntity.accepted()
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(this.userService.authenticateUser(loginDTO));
    }

}

package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.UserDTO;
import cal.projeteq3.glucose.dto.LoginDTO;
import cal.projeteq3.glucose.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public final class AuthController{
	private final UserService userService;

	public AuthController(UserService userService){
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<UserDTO> Login(@RequestBody LoginDTO loginDTO){
		UserDTO user = userService.authenticateUser(loginDTO);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.badRequest().build();
	}

}

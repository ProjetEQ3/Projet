package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.auth.JWTAuthResponse;
import cal.projeteq3.glucose.auth.LoginDTO;
import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/auth")
public final class AuthController{
	private final UtilisateurService utilisateurService;

	public AuthController(UtilisateurService utilisateurService){
		this.utilisateurService = utilisateurService;
	}

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDto){
		try{
			return ResponseEntity.ok(new JWTAuthResponse(utilisateurService.authenticateUser(loginDto)));
		}catch(APIException e){
			return ResponseEntity.status(e.getStatus()).body(new JWTAuthResponse(List.of(e.getMessage())));
		}
		catch(Exception e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JWTAuthResponse(List.of("Unknown error")));
		}
	}

}

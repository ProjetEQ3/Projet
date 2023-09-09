package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.model.auth.JWTAuthResponse;
import cal.projeteq3.glucose.model.auth.LoginDTO;
import cal.projeteq3.glucose.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public final class AuthController{
	private final UtilisateurService utilisateurService;

	public AuthController(UtilisateurService utilisateurService){
		this.utilisateurService = utilisateurService;
	}

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> Login(@RequestBody LoginDTO loginDto){
		return ResponseEntity.ok(new JWTAuthResponse(utilisateurService.authenticateUser(loginDto)));
	}

}

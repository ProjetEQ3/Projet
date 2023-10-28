package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.SemesterDTO;
import cal.projeteq3.glucose.dto.auth.JWTAuthResponse;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.contract.ShortContractDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.service.UserService;
import cal.projeteq3.glucose.validation.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController{

	private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDto){
            return ResponseEntity.accepted()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new JWTAuthResponse(userService.authenticateUser(loginDto)));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(HttpServletRequest request){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getMe(request.getHeader("Authorization")));
    }

    @GetMapping("/semesters")
    public ResponseEntity<List<SemesterDTO>> getSemesters(){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getSemesters());
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<ShortContractDTO> getContract(@PathVariable Long contractId){
        return ResponseEntity.accepted()
                .contentType(MediaType.APPLICATION_PDF)
                .body(userService.getContractById(contractId));
    }
}

package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.exception.badRequestException.UserNotFoundException;
import cal.projeteq3.glucose.model.user.*;
import cal.projeteq3.glucose.repository.*;
import cal.projeteq3.glucose.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final StudentRepository studentRepository;
	private final EmployerRepository employerRepository;
	private final ManagerRepository managerRepository;
	private final CredentialRepository credentialRepository;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	public String authenticateUser(LoginDTO loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		return jwtTokenProvider.generateToken(authentication);
	}

	private ManagerDTO getManagerDto(Long id) {
		Optional<Manager> optManager = managerRepository.findById(id);
		if (optManager.isEmpty()) throw new UserNotFoundException("No Manager found with this ID: " + id);

		return new ManagerDTO(optManager.get());
	}

	private EmployerDTO getEmployerDto(Long id) {
		Optional<Employer> optEmployer = employerRepository.findById(id);
		if (optEmployer.isEmpty()) throw new UserNotFoundException("No Employer found with this ID: " + id);

		return new EmployerDTO(optEmployer.get());
	}

	private StudentDTO getStudentDto(Long id) {
		Optional<StudentSummary> optStudent = studentRepository.findStudentSummaryById(id);
		if (optStudent.isEmpty()) throw new UserNotFoundException("No Student found with this ID: " + id);

		return new StudentDTO(optStudent.get());
	}

	public UserDTO getMe(String token) {
		String email = jwtTokenProvider.getEmailFromJWT(token);
		User user = userRepository.findUserByCredentialsEmail(email)
				.orElseThrow(() -> new UserNotFoundException("No User found with this email: " + email));

        return switch (user.getRole()) {
            case STUDENT -> getStudentDto(user.getId());
            case EMPLOYER -> getEmployerDto(user.getId());
            case MANAGER -> getManagerDto(user.getId());
        };
    }
}

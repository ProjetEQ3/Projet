package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.EmailDetailsDTO;
import cal.projeteq3.glucose.dto.SemesterDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.exception.badRequestException.ContractNotFoundException;
import cal.projeteq3.glucose.exception.badRequestException.SendEmailException;
import cal.projeteq3.glucose.exception.badRequestException.UserNotFoundException;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.user.*;
import cal.projeteq3.glucose.repository.*;
import cal.projeteq3.glucose.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{
	private final StudentRepository studentRepository;
	private final ContractRepository contractRepository;
	private final EmployerRepository employerRepository;
	private final ManagerRepository managerRepository;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	public String authenticateUser(LoginDTO loginDto){
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		return jwtTokenProvider.generateToken(authentication);
	}

	public Long authenticateUserContractSigning(LoginDTO loginDto){
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
		);
		return userRepository
			.findUserByCredentialsEmail(loginDto.getEmail())
			.orElseThrow(UserNotFoundException::new)
		  .getId();
	}

	public UserDTO getMe(String token){
		String email = jwtTokenProvider.getEmailFromJWT(token);
		User user = userRepository.findUserByCredentialsEmail(email).orElseThrow(UserNotFoundException::new);
		return switch(user.getRole()){
			case STUDENT -> getStudentDto(user.getId());
			case EMPLOYER -> getEmployerDto(user.getId());
			case MANAGER -> getManagerDto(user.getId());
		};
	}

	private ManagerDTO getManagerDto(Long id){
		return new ManagerDTO(managerRepository.findById(id).orElseThrow(UserNotFoundException::new));
	}

	private EmployerDTO getEmployerDto(Long id){
		return new EmployerDTO(employerRepository.findById(id).orElseThrow(UserNotFoundException::new));
	}

	private StudentDTO getStudentDto(Long id){
		return new StudentDTO(studentRepository.findById(id).orElseThrow(UserNotFoundException::new));
	}

	public List<SemesterDTO> getSemesters(){
		int nbSeasons = 5;
		List<Semester> semesters = new ArrayList<>();
		Semester currentSemester = new Semester(LocalDate.now());

		semesters.add(currentSemester.nextSemester());

		for(int i = 0;i < nbSeasons;i++){
			semesters.add(currentSemester);
			currentSemester = currentSemester.previousSemester();
		}

		return semesters.stream().map(SemesterDTO::new).toList();
	}

	public ContractDTO getShortContractById(Long id){
		Contract contract = contractRepository.findById(id).orElseThrow(ContractNotFoundException::new);
		Manager manager = managerRepository.findFirstByDepartment(contract.getJobOffer().getDepartment());
		return new ContractDTO(contract, manager);
	}

	public List<ContractDTO> getAllContracts(){
		return contractRepository.findAll().stream().map(ContractDTO::new).toList();
	}

	public List<ContractDTO> getAllShortContracts(){
		return contractRepository
			.findAll()
			.stream()
			.map((contract -> new ContractDTO(
				contract, managerRepository.findFirstByDepartment(contract.getJobOffer().getDepartment())
			)))
			.toList();
	}
}

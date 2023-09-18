package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.exception.request.UserAlreadyExistsException;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.User;
import cal.projeteq3.glucose.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
	private final StudentRepository studentRepository;
	private final EmployerRepository employerRepository;
	private final ManagerRepository managerRepository;
	private final CredentialRepository credentialRepository;
	private final UserRepository userRepository;

	public UserService(
			StudentRepository studentRepository, EmployerRepository employerRepository,
			ManagerRepository managerRepository, CredentialRepository credentialRepository, UserRepository userRepository
	){
		this.studentRepository = studentRepository;
		this.employerRepository = employerRepository;
		this.managerRepository = managerRepository;
		this.credentialRepository = credentialRepository;
		this.userRepository = userRepository;
	}

	/*public UserDTO authenticateUser(LoginDTO loginDTO){
		User user;
		if(loginDTO.getEmail() != null && loginDTO.getPassword() != null) {
			user = studentRepository.findByEmail(loginDTO.getEmail()).orElse(null);
			if (user == null)
				user = employerRepository.findByEmail(loginDTO.getEmail()).orElse(null);
			if (user == null)
				user = managerRepository.findByEmail(loginDTO.getEmail()).orElse(null);
			if (user == null)
				throw new UserNotFoundException(loginDTO.getEmail());
			if(user.getPassword().equals(loginDTO.getPassword())){
				//return user.toDTO();
			}
		} else {
			throw new IllegalArgumentException();
		}
		return null;
    }*/

	public UserDTO authenticateUser(LoginDTO loginDTO){
		User user;
//		if (loginDTO.getEmail() == null || loginDTO.getPassword() == null) throw new IllegalArgumentException("Null email or password");
        Optional<Credentials> optCred = credentialRepository.findCredentialsByEmail(loginDTO.getEmail());
        if (optCred.isEmpty()) throw new UserNotFoundException("No user for email" + loginDTO.getEmail());

		Optional<User> optUser = userRepository.findUserByCredentialsEmail(loginDTO.getEmail());
		user = optUser.get();

		System.out.println("AUTH USER: " + user);
		if (!user.getPassword().equals(loginDTO.getPassword())) throw new ValidationException("Invalid User email or Password");


        return switch (user.getRole()){
            case STUDENT -> getStudentDto(user.getId());
            case EMPLOYER -> getEmployerDto(user.getId());
            case MANAGER -> getManagerDto(user.getId());
            default -> throw new UserNotFoundException("No such role is known");
        };
    }

	public UserDTO createUser(RegisterDTO registerDTO){
		if(userRepository.findCredentials(registerDTO.getEmail()).isPresent())
			throw new UserAlreadyExistsException(registerDTO.getEmail());
		User user = switch(registerDTO.getRole()){
			case "STUDENT" -> Student.builder().email(registerDTO.getEmail()).password(registerDTO.getPassword()).build();
			case "EMPLOYER" -> Employer.builder().email(registerDTO.getEmail()).password(registerDTO.getPassword()).build();
			case "MANAGER" -> Manager.builder().email(registerDTO.getEmail()).password(registerDTO.getPassword()).build();
			default -> throw new IllegalArgumentException();
		};
		User savedUser = userRepository.save(user);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(savedUser.getId());
		userDTO.setEmail(savedUser.getEmail());
		userDTO.setRole(savedUser.getCredentials().getRole().toString());
		return userDTO;
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
		Optional<Student> optStudent = studentRepository.findById(id);
		if (optStudent.isEmpty()) throw new UserNotFoundException("No Student found with this ID: " + id);

		return new StudentDTO(optStudent.get());
	}
}

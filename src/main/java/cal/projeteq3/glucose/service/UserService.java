package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.exception.request.UserAlreadyExistsException;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.User;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import cal.projeteq3.glucose.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final StudentRepository studentRepository;
	private final EmployerRepository employerRepository;
	private final ManagerRepository managerRepository;
	private final UserRepository userRepository;

	public UserService(
		StudentRepository studentRepository, EmployerRepository employerRepository,
		ManagerRepository managerRepository, UserRepository userRepository
	){
		this.studentRepository = studentRepository;
		this.employerRepository = employerRepository;
		this.managerRepository = managerRepository;
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

}

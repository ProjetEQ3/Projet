package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.model.user.User;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final StudentRepository studentRepository;
	private final EmployerRepository employerRepository;
	private final ManagerRepository managerRepository;

	public UserService(StudentRepository studentRepository, EmployerRepository employerRepository, ManagerRepository managerRepository){
		this.studentRepository = studentRepository;
		this.employerRepository = employerRepository;
		this.managerRepository = managerRepository;
	}

	public UserDTO authenticateUser(LoginDTO loginDTO){
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
				return user.toDTO();
			}
		} else {
			throw new IllegalArgumentException();
		}
		return null;
    }
}

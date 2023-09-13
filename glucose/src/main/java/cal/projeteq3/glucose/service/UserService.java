package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.UserDTO;
import cal.projeteq3.glucose.dto.LoginDTO;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.model.User;
import cal.projeteq3.glucose.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	public UserDTO authenticateUser(LoginDTO loginDTO){
		User user;
		if(loginDTO.getEmail() != null && loginDTO.getPassword() != null) {
			user = userRepository.findByEmail(loginDTO.getEmail()).orElse(null);
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

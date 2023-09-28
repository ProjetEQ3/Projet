package cal.projeteq3.glucose.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.exception.request.RoleNotHandled;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.User;
import cal.projeteq3.glucose.repository.CredentialRepository;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import cal.projeteq3.glucose.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {UserService.class})
public class UserServiceTest {

    @MockBean
    private CredentialRepository credentialRepository;

    @MockBean
    private EmployerRepository employerRepository;

    @MockBean
    private ManagerRepository managerRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void authenticateStudent_validCredentials() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("user@example.com");
        loginDTO.setPassword("password");

        Credentials credentials = new Credentials();
        credentials.setEmail("user@example.com");
        credentials.setPassword("password");
        credentials.setRole(Role.STUDENT);

        Student user = Student.builder()
                .id(1L)
                .email(credentials.getEmail())
                .password(credentials.getPassword())
                .build();
        user.setCredentials(credentials);

        when(credentialRepository.findCredentialsByEmail(loginDTO.getEmail())).thenReturn(Optional.of(credentials));
        when(userRepository.findUserByCredentialsEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(studentRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        UserDTO userDTO = userService.authenticateUser(loginDTO);

        // Assert
        assertNotNull(userDTO);
        assertTrue(userDTO instanceof StudentDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getCredentials().getRole().toString(), userDTO.getRole());
        verify(credentialRepository).findCredentialsByEmail(loginDTO.getEmail());
    }

    @Test
    public void authenticateManager_validCredentials() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("manager@example.com");
        loginDTO.setPassword("password");

        Credentials credentials = new Credentials();
        credentials.setEmail("manager@example.com");
        credentials.setPassword("password");
        credentials.setRole(Role.MANAGER);

        Manager user = Manager.builder()
                .firstName("John")
                .lastName("Doe")
                .email(credentials.getEmail())
                .password(credentials.getPassword())
                .build();

        when(credentialRepository.findCredentialsByEmail(loginDTO.getEmail())).thenReturn(Optional.of(credentials));
        when(userRepository.findUserByCredentialsEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(managerRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        UserDTO userDTO = userService.authenticateUser(loginDTO);

        // Assert
        assertNotNull(userDTO);
        assertTrue(userDTO instanceof ManagerDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        verify(credentialRepository).findCredentialsByEmail(loginDTO.getEmail());
    }

    @Test
    public void authenticateEmployer_validCredentials() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("employer@example.com");
        loginDTO.setPassword("password");

        Credentials credentials = new Credentials();
        credentials.setEmail("employer@example.com");
        credentials.setPassword("password");
        credentials.setRole(Role.EMPLOYER);

        Employer user = Employer.builder()
                .firstName("John")
                .lastName("Doe")
                .email(credentials.getEmail())
                .password(credentials.getPassword())
                .build();

        when(credentialRepository.findCredentialsByEmail(loginDTO.getEmail())).thenReturn(Optional.of(credentials));
        when(userRepository.findUserByCredentialsEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(employerRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        UserDTO userDTO = userService.authenticateUser(loginDTO);

        // Assert
        assertNotNull(userDTO);
        assertTrue(userDTO instanceof EmployerDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        verify(credentialRepository).findCredentialsByEmail(loginDTO.getEmail());
    }

    @Test
    public void authenticateUser_invalidCredentials() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("user@example.com");
        loginDTO.setPassword("wrongpassword");

        Credentials credentials = new Credentials();
        credentials.setEmail("user@example.com");
        credentials.setPassword("password");

        when(credentialRepository.findCredentialsByEmail(loginDTO.getEmail())).thenReturn(Optional.of(credentials));

        // Act and Assert
        assertThrows(ValidationException.class, () -> {
            userService.authenticateUser(loginDTO);
        });
        verify(credentialRepository).findCredentialsByEmail(loginDTO.getEmail());
    }

    @Test
    public void authenticateAny_userNotFound() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("manager@example.com");
        loginDTO.setPassword("password");

        when(credentialRepository.findCredentialsByEmail(loginDTO.getEmail())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.authenticateUser(loginDTO);
        });
        verify(credentialRepository).findCredentialsByEmail(loginDTO.getEmail());
    }
}

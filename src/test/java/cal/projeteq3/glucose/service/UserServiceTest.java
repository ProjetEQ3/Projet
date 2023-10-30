package cal.projeteq3.glucose.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.contract.ShortContractDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.exception.badRequestException.UserNotFoundException;
import cal.projeteq3.glucose.exception.badRequestException.ValidationException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.User;
import cal.projeteq3.glucose.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cal.projeteq3.glucose.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
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
    private ContractRepository contractRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    private final String studentToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb3Vpc0BtaWNoYXVkLmNvbSIsImlhdCI6MTY5NzE1OTMyMSwiZXhwIjoxNjk3MjQ1NzIxLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiU1RVREVOVCJ9XX0.WaylpDI3GUw-pTmdGO1neDurQ-gJwqmUZHLpP1y_zwU";
    private final String managerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoZWxAbWljaGF1ZC5jb20iLCJpYXQiOjE2OTcxNTk1ODksImV4cCI6MTY5NzI0NTk4OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6Ik1BTkFHRVIifV19.KX_Jv05ER3d6klRxjlk18HWZKEaDE0TRtt_XxuE3iu0";
    private final String employerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb3Vpc0Bwcm9mZXNzaW9ubmVsLmNvbSIsImlhdCI6MTY5NzE1OTYwMywiZXhwIjoxNjk3MjQ2MDAzLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiRU1QTE9ZRVIifV19.cL4f0qWlyAnLATSor2GmoDjy8QJbbPYVPGKFTOXD-_0";

    @BeforeEach
    public void setUp() {
        when(jwtTokenProvider.generateToken(any())).thenReturn("token");
        when(authenticationManager.authenticate(any())).thenReturn(null);
    }

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
        String token = userService.authenticateUser(loginDTO);

        // Assert
        assertNotNull(token);
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
        String token = userService.authenticateUser(loginDTO);

        // Assert
        assertNotNull(token);
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
        String token = userService.authenticateUser(loginDTO);

        // Assert
        assertNotNull(token);
    }


    @Test
    void GetMe_ValidStudent() {
        // Arrange
        String email = "janedoe@example.com";
        Student studentUser = Student.builder()
                .id(1L)
                .email(email)
                .password("Test12345")
                .build();

        when(jwtTokenProvider.getEmailFromJWT(studentToken)).thenReturn(email);
        when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.of(studentUser));
        when(studentRepository.findById(studentUser.getId())).thenReturn(Optional.of(studentUser));

        // Act
        UserDTO userDTO = userService.getMe(studentToken);

        // Assert
        assertNotNull(userDTO);
        assertEquals(Role.STUDENT, studentUser.getRole());
    }

    @Test
    void GetMe_ValidManager() {
        // Arrange
        String email = "manager@example.com";
        Manager managerUser = Manager.builder()
                .id(2L)
                .email(email)
                .password("Test12345")
                .build();

        when(jwtTokenProvider.getEmailFromJWT(managerToken)).thenReturn(email);
        when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.of(managerUser));
        when(managerRepository.findById(managerUser.getId())).thenReturn(Optional.of(managerUser));

        // Act
        UserDTO userDTO = userService.getMe(managerToken);

        // Assert
        assertNotNull(userDTO);
        assertEquals(Role.MANAGER, managerUser.getRole());
    }

    @Test
    void GetMe_ValidEmployer() {
        // Arrange
        String email = "employer@example.com";
        Employer employerUser = Employer.builder()
                .id(3L)
                .email(email)
                .password("Test12345")
                .build();

        when(jwtTokenProvider.getEmailFromJWT(employerToken)).thenReturn(email);
        when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.of(employerUser));
        when(employerRepository.findById(employerUser.getId())).thenReturn(Optional.of(employerUser));

        // Act
        UserDTO userDTO = userService.getMe(employerToken);

        // Assert
        assertNotNull(userDTO);
        assertEquals(Role.EMPLOYER, employerUser.getRole());
    }


    @Test
    void GetMe_UserNotFound() {
        // Arrange
        String email = "janedoe@example.com";

        when(jwtTokenProvider.getEmailFromJWT(studentToken)).thenReturn(email);
        when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getMe(studentToken));
    }

    @Test
    void GetMe_InvalidManager() {
        // Arrange
        String email = "manager@example.com";

        Manager managerUser = Manager.builder()
                .email(email)
                .password("Test12345")
                .build();

        when(jwtTokenProvider.getEmailFromJWT(managerToken)).thenReturn(email);
        when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.of(managerUser));
        when(managerRepository.findById(any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getMe(managerToken));
    }

    @Test
    void GetMe_InvalidEmployer() {
        // Arrange
        String email = "employer@example.com";

        Employer employerUser = Employer.builder()
                .email(email)
                .password("Test12345")
                .build();

        when(jwtTokenProvider.getEmailFromJWT(employerToken)).thenReturn(email);
        when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.of(employerUser));
        when(employerRepository.findById(any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getMe(employerToken));
    }

    @Test
    void GetMe_InvalidStudent() {
        // Arrange
        String email = "employer@example.com";

        Student studentUser = Student.builder()
                .email(email)
                .password("Test12345")
                .build();

        when(jwtTokenProvider.getEmailFromJWT(studentToken)).thenReturn(email);
        when(userRepository.findUserByCredentialsEmail(email)).thenReturn(Optional.of(studentUser));
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getMe(studentToken));
    }

    @Test
    void GetSemesters() {
        // Arrange
        List<Semester> semesters = new ArrayList<>();
        semesters.add(new Semester(LocalDate.now().plusMonths(4)));
        semesters.add(new Semester(LocalDate.now()));
        semesters.add(new Semester(LocalDate.now().minusMonths(4)));
        semesters.add(new Semester(LocalDate.now().minusMonths(8)));
        semesters.add(new Semester(LocalDate.now().minusMonths(12)));
        semesters.add(new Semester(LocalDate.now().minusMonths(16)));

        // Act and Assert
        assertEquals(semesters.size(), userService.getSemesters().size());
        assert semesters.get(0).equals(userService.getSemesters().get(0).toEntity());
        assert semesters.get(1).equals(userService.getSemesters().get(1).toEntity());
        assert semesters.get(2).equals(userService.getSemesters().get(2).toEntity());
        assert semesters.get(3).equals(userService.getSemesters().get(3).toEntity());
        assert semesters.get(4).equals(userService.getSemesters().get(4).toEntity());
//        AssertEquals doesn't work for some obscure reason
//        assertEquals(semesters.get(0), userService.getSemesters().get(0).toEntity());
//        assertEquals(semesters.get(1), userService.getSemesters().get(1).toEntity());
//        assertEquals(semesters.get(2), userService.getSemesters().get(2).toEntity());
//        assertEquals(semesters.get(3), userService.getSemesters().get(3).toEntity());
//        assertEquals(semesters.get(4), userService.getSemesters().get(4).toEntity());

    }

    @Test
    public void getContractsBySession() {
        // Arrange
        Credentials credManager = new Credentials();
        credManager.setEmail("Michel@Michaud.com");
        credManager.setRole(Role.MANAGER);

        Credentials credEmployer = new Credentials();
        credEmployer.setEmail("Michel@Professionel.com");
        credEmployer.setRole(Role.EMPLOYER);

        Credentials credStudent = new Credentials();
        credStudent.setEmail("Michel@Student.com");
        credStudent.setRole(Role.STUDENT);

        Manager manager = new Manager();
        manager.setCredentials(credManager);
        manager.setId(1L);
        manager.setFirstName("Michel");
        manager.setLastName("Michaud");

        Employer employer = new Employer();
        employer.setCredentials(credEmployer);
        employer.setId(2L);
        employer.setFirstName("Michel");
        employer.setLastName("Professionel");
        employer.setOrganisationName("Professionel");
        employer.setOrganisationPhone("111-111-1111");

        Student student = new Student();
        student.setId(3L);
        student.setCredentials(credStudent);
        student.setFirstName("Michel");
        student.setLastName("Student");

        Semester semester = new Semester(LocalDate.now());

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(4L);
        jobOffer.setEmployer(employer);
        jobOffer.setSemester(semester);
        jobOffer.setDepartment(Department._420B0);
        jobOffer.setJobOfferState(JobOfferState.OPEN);
        jobOffer.setDuration(6);
        jobOffer.setHoursPerWeek(40);
        jobOffer.setSalary(20.0f);
        jobOffer.setStartDate(LocalDate.now());
        jobOffer.setExpirationDate(LocalDate.now().plusDays(30));
        jobOffer.setLocation("Location1");
        jobOffer.setDescription("Description1");
        jobOffer.setTitle("JobOffer1");

        Contract contract = new Contract(employer, student, jobOffer);
        contract.setId(1L);
        System.out.println(contract.getId());

        contractRepository.save(contract);
        when(contractRepository.findAll()).thenReturn(List.of(contract));
        when(managerRepository.findAll()).thenReturn(List.of(manager));
        when(employerRepository.findAll()).thenReturn(List.of(employer));
        when(studentRepository.findAll()).thenReturn(List.of(student));

        // Act
        ShortContractDTO shortContractDTO = userService.getShortContractById(1L);
        assertEquals(contract.getId(), shortContractDTO.getId());
    }
}

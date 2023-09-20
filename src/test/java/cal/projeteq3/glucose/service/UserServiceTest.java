package cal.projeteq3.glucose.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.exception.request.UserAlreadyExistsException;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.User;
import cal.projeteq3.glucose.repository.CredentialRepository;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import cal.projeteq3.glucose.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAuthenticateUser() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "cal.projeteq3.glucose.model.auth.Credentials.getRole()" because "this.credentials" is null
        //       at cal.projeteq3.glucose.model.user.User.getRole(User.java:43)
        //       at cal.projeteq3.glucose.service.UserService.authenticateUser(UserService.java:52)
        //   See https://diff.blue/R013 to resolve this issue.

        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());
        Optional<Credentials> ofResult = Optional.of(credentials);
        when(credentialRepository.findCredentialsByEmail(Mockito.<String>any())).thenReturn(ofResult);
        Optional<User> ofResult2 = Optional.of(new Employer());
        when(userRepository.findUserByCredentialsEmail(Mockito.<String>any())).thenReturn(ofResult2);
        userService.authenticateUser(new LoginDTO("jane.doe@example.org", "iloveyou"));
    }

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    void testAuthenticateUser2() {
        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());
        Optional<Credentials> ofResult = Optional.of(credentials);
        when(credentialRepository.findCredentialsByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(userRepository.findUserByCredentialsEmail(Mockito.<String>any()))
                .thenThrow(new UserNotFoundException("jane.doe@example.org"));
        assertThrows(UserNotFoundException.class,
                () -> userService.authenticateUser(new LoginDTO("jane.doe@example.org", "iloveyou")));
        verify(credentialRepository).findCredentialsByEmail(Mockito.<String>any());
        verify(userRepository).findUserByCredentialsEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    void testAuthenticateUser3() {
        Optional<Credentials> emptyResult = Optional.empty();
        when(credentialRepository.findCredentialsByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(UserNotFoundException.class,
                () -> userService.authenticateUser(new LoginDTO("jane.doe@example.org", "iloveyou")));
        verify(credentialRepository).findCredentialsByEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    void testAuthenticateUser4() {
        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());
        Optional<Credentials> ofResult = Optional.of(credentials);
        when(credentialRepository.findCredentialsByEmail(Mockito.<String>any())).thenReturn(ofResult);

        Credentials credentials2 = new Credentials();
        credentials2.setEmail("jane.doe@example.org");
        credentials2.setId(1L);
        credentials2.setPassword("iloveyou");
        credentials2.setRole(Role.ADMIN);
        credentials2.setUser(new Employer());

        Manager manager = new Manager();
        manager.setCredentials(credentials2);
        manager.setEmail("jane.doe@example.org");
        manager.setFirstName("Jane");
        manager.setId(1L);
        manager.setLastName("Doe");
        manager.setMatricule("iloveyou");
        manager.setPassword("iloveyou");
        manager.setPhoneNumber("6625550144");
        manager.setRole(Role.ADMIN);
        Optional<User> ofResult2 = Optional.of(manager);
        when(userRepository.findUserByCredentialsEmail(Mockito.<String>any())).thenReturn(ofResult2);
        assertThrows(UserNotFoundException.class,
                () -> userService.authenticateUser(new LoginDTO("jane.doe@example.org", "iloveyou")));
        verify(credentialRepository).findCredentialsByEmail(Mockito.<String>any());
        verify(userRepository).findUserByCredentialsEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    void testAuthenticateUser5() {
        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());

        Employer employer = new Employer();
        employer.setCredentials(credentials);
        employer.setEmail("jane.doe@example.org");
        employer.setFirstName("Jane");
        employer.setId(1L);
        employer.setJobOffers(new ArrayList<>());
        employer.setLastName("Doe");
        employer.setOrganisationName("Organisation Name");
        employer.setOrganisationPhone("6625550144");
        employer.setPassword("iloveyou");
        employer.setRole(Role.ADMIN);
        Optional<Employer> ofResult = Optional.of(employer);
        when(employerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Credentials credentials2 = new Credentials();
        credentials2.setEmail("jane.doe@example.org");
        credentials2.setId(1L);
        credentials2.setPassword("iloveyou");
        credentials2.setRole(Role.ADMIN);
        credentials2.setUser(new Employer());
        Optional<Credentials> ofResult2 = Optional.of(credentials2);
        when(credentialRepository.findCredentialsByEmail(Mockito.<String>any())).thenReturn(ofResult2);
        Employer.EmployerBuilder idResult = Employer.builder().email("jane.doe@example.org").firstName("Jane").id(1L);
        Optional<User> ofResult3 = Optional.of(idResult.jobOffers(new ArrayList<>())
                .lastName("Doe")
                .organisationName("Organisation Name")
                .organisationPhone("6625550144")
                .password("iloveyou")
                .build());
        when(userRepository.findUserByCredentialsEmail(Mockito.<String>any())).thenReturn(ofResult3);
        UserDTO actualAuthenticateUserResult = userService
                .authenticateUser(new LoginDTO("jane.doe@example.org", "iloveyou"));
        assertEquals("jane.doe@example.org", actualAuthenticateUserResult.getEmail());
        assertEquals("ROLE_ADMIN", actualAuthenticateUserResult.getRole());
        assertEquals("6625550144", ((EmployerDTO) actualAuthenticateUserResult).getOrganisationPhone());
        assertEquals("Organisation Name", ((EmployerDTO) actualAuthenticateUserResult).getOrganisationName());
        assertEquals("Doe", actualAuthenticateUserResult.getLastName());
        assertTrue(((EmployerDTO) actualAuthenticateUserResult).getJobOffers().isEmpty());
        assertEquals(1L, actualAuthenticateUserResult.getId().longValue());
        assertEquals("Jane", actualAuthenticateUserResult.getFirstName());
        verify(employerRepository).findById(Mockito.<Long>any());
        verify(credentialRepository).findCredentialsByEmail(Mockito.<String>any());
        verify(userRepository).findUserByCredentialsEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    void testAuthenticateUser6() {
        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());

        Credentials credentials2 = new Credentials();
        credentials2.setEmail("jane.doe@example.org");
        credentials2.setId(1L);
        credentials2.setPassword("iloveyou");
        credentials2.setRole(Role.ADMIN);
        credentials2.setUser(new Employer());
        Employer employer = mock(Employer.class);
        when(employer.getOrganisationName()).thenReturn("Organisation Name");
        when(employer.getOrganisationPhone()).thenReturn("6625550144");
        when(employer.getJobOffers()).thenReturn(new ArrayList<>());
        when(employer.getCredentials()).thenReturn(credentials2);
        when(employer.getId()).thenReturn(1L);
        when(employer.getEmail()).thenReturn("jane.doe@example.org");
        when(employer.getFirstName()).thenReturn("Jane");
        when(employer.getLastName()).thenReturn("Doe");
        doNothing().when(employer).setJobOffers(Mockito.<List<JobOffer>>any());
        doNothing().when(employer).setOrganisationName(Mockito.<String>any());
        doNothing().when(employer).setOrganisationPhone(Mockito.<String>any());
        doNothing().when(employer).setCredentials(Mockito.<Credentials>any());
        doNothing().when(employer).setEmail(Mockito.<String>any());
        doNothing().when(employer).setFirstName(Mockito.<String>any());
        doNothing().when(employer).setId(Mockito.<Long>any());
        doNothing().when(employer).setLastName(Mockito.<String>any());
        doNothing().when(employer).setPassword(Mockito.<String>any());
        doNothing().when(employer).setRole(Mockito.<Role>any());
        employer.setCredentials(credentials);
        employer.setEmail("jane.doe@example.org");
        employer.setFirstName("Jane");
        employer.setId(1L);
        employer.setJobOffers(new ArrayList<>());
        employer.setLastName("Doe");
        employer.setOrganisationName("Organisation Name");
        employer.setOrganisationPhone("6625550144");
        employer.setPassword("iloveyou");
        employer.setRole(Role.ADMIN);
        Optional<Employer> ofResult = Optional.of(employer);
        when(employerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Credentials credentials3 = new Credentials();
        credentials3.setEmail("jane.doe@example.org");
        credentials3.setId(1L);
        credentials3.setPassword("iloveyou");
        credentials3.setRole(Role.ADMIN);
        credentials3.setUser(new Employer());
        Optional<Credentials> ofResult2 = Optional.of(credentials3);
        when(credentialRepository.findCredentialsByEmail(Mockito.<String>any())).thenReturn(ofResult2);
        Employer.EmployerBuilder idResult = Employer.builder().email("jane.doe@example.org").firstName("Jane").id(1L);
        Optional<User> ofResult3 = Optional.of(idResult.jobOffers(new ArrayList<>())
                .lastName("Doe")
                .organisationName("Organisation Name")
                .organisationPhone("6625550144")
                .password("iloveyou")
                .build());
        when(userRepository.findUserByCredentialsEmail(Mockito.<String>any())).thenReturn(ofResult3);
        UserDTO actualAuthenticateUserResult = userService
                .authenticateUser(new LoginDTO("jane.doe@example.org", "iloveyou"));
        assertEquals("jane.doe@example.org", actualAuthenticateUserResult.getEmail());
        assertEquals("ROLE_ADMIN", actualAuthenticateUserResult.getRole());
        assertEquals("6625550144", ((EmployerDTO) actualAuthenticateUserResult).getOrganisationPhone());
        assertEquals("Organisation Name", ((EmployerDTO) actualAuthenticateUserResult).getOrganisationName());
        assertEquals("Doe", actualAuthenticateUserResult.getLastName());
        assertTrue(((EmployerDTO) actualAuthenticateUserResult).getJobOffers().isEmpty());
        assertEquals(1L, actualAuthenticateUserResult.getId().longValue());
        assertEquals("Jane", actualAuthenticateUserResult.getFirstName());
        verify(employerRepository).findById(Mockito.<Long>any());
        verify(employer).getCredentials();
        verify(employer).getId();
        verify(employer).getOrganisationName();
        verify(employer).getOrganisationPhone();
        verify(employer).getEmail();
        verify(employer).getFirstName();
        verify(employer).getLastName();
        verify(employer, atLeast(1)).getJobOffers();
        verify(employer).setJobOffers(Mockito.<List<JobOffer>>any());
        verify(employer).setOrganisationName(Mockito.<String>any());
        verify(employer).setOrganisationPhone(Mockito.<String>any());
        verify(employer).setCredentials(Mockito.<Credentials>any());
        verify(employer).setEmail(Mockito.<String>any());
        verify(employer).setFirstName(Mockito.<String>any());
        verify(employer).setId(Mockito.<Long>any());
        verify(employer).setLastName(Mockito.<String>any());
        verify(employer).setPassword(Mockito.<String>any());
        verify(employer).setRole(Mockito.<Role>any());
        verify(credentialRepository).findCredentialsByEmail(Mockito.<String>any());
        verify(userRepository).findUserByCredentialsEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    void testAuthenticateUser7() {
        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());

        Credentials credentials2 = new Credentials();
        credentials2.setEmail("jane.doe@example.org");
        credentials2.setId(1L);
        credentials2.setPassword("iloveyou");
        credentials2.setRole(Role.ADMIN);
        credentials2.setUser(new Employer());
        Employer employer = mock(Employer.class);
        when(employer.getOrganisationName()).thenThrow(new IllegalArgumentException("iloveyou"));
        when(employer.getCredentials()).thenReturn(credentials2);
        when(employer.getId()).thenReturn(1L);
        when(employer.getEmail()).thenReturn("jane.doe@example.org");
        when(employer.getFirstName()).thenReturn("Jane");
        when(employer.getLastName()).thenReturn("Doe");
        doNothing().when(employer).setJobOffers(Mockito.<List<JobOffer>>any());
        doNothing().when(employer).setOrganisationName(Mockito.<String>any());
        doNothing().when(employer).setOrganisationPhone(Mockito.<String>any());
        doNothing().when(employer).setCredentials(Mockito.<Credentials>any());
        doNothing().when(employer).setEmail(Mockito.<String>any());
        doNothing().when(employer).setFirstName(Mockito.<String>any());
        doNothing().when(employer).setId(Mockito.<Long>any());
        doNothing().when(employer).setLastName(Mockito.<String>any());
        doNothing().when(employer).setPassword(Mockito.<String>any());
        doNothing().when(employer).setRole(Mockito.<Role>any());
        employer.setCredentials(credentials);
        employer.setEmail("jane.doe@example.org");
        employer.setFirstName("Jane");
        employer.setId(1L);
        employer.setJobOffers(new ArrayList<>());
        employer.setLastName("Doe");
        employer.setOrganisationName("Organisation Name");
        employer.setOrganisationPhone("6625550144");
        employer.setPassword("iloveyou");
        employer.setRole(Role.ADMIN);
        Optional<Employer> ofResult = Optional.of(employer);
        when(employerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Credentials credentials3 = new Credentials();
        credentials3.setEmail("jane.doe@example.org");
        credentials3.setId(1L);
        credentials3.setPassword("iloveyou");
        credentials3.setRole(Role.ADMIN);
        credentials3.setUser(new Employer());
        Optional<Credentials> ofResult2 = Optional.of(credentials3);
        when(credentialRepository.findCredentialsByEmail(Mockito.<String>any())).thenReturn(ofResult2);
        Employer.EmployerBuilder idResult = Employer.builder().email("jane.doe@example.org").firstName("Jane").id(1L);
        Optional<User> ofResult3 = Optional.of(idResult.jobOffers(new ArrayList<>())
                .lastName("Doe")
                .organisationName("Organisation Name")
                .organisationPhone("6625550144")
                .password("iloveyou")
                .build());
        when(userRepository.findUserByCredentialsEmail(Mockito.<String>any())).thenReturn(ofResult3);
        assertThrows(IllegalArgumentException.class,
                () -> userService.authenticateUser(new LoginDTO("jane.doe@example.org", "iloveyou")));
        verify(employerRepository).findById(Mockito.<Long>any());
        verify(employer).getCredentials();
        verify(employer).getId();
        verify(employer).getOrganisationName();
        verify(employer).getEmail();
        verify(employer).getFirstName();
        verify(employer).getLastName();
        verify(employer).setJobOffers(Mockito.<List<JobOffer>>any());
        verify(employer).setOrganisationName(Mockito.<String>any());
        verify(employer).setOrganisationPhone(Mockito.<String>any());
        verify(employer).setCredentials(Mockito.<Credentials>any());
        verify(employer).setEmail(Mockito.<String>any());
        verify(employer).setFirstName(Mockito.<String>any());
        verify(employer).setId(Mockito.<Long>any());
        verify(employer).setLastName(Mockito.<String>any());
        verify(employer).setPassword(Mockito.<String>any());
        verify(employer).setRole(Mockito.<Role>any());
        verify(credentialRepository).findCredentialsByEmail(Mockito.<String>any());
        verify(userRepository).findUserByCredentialsEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#createUser(RegisterDTO)}
     */
    @Test
    void testCreateUser() {
        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());
        Optional<Credentials> ofResult = Optional.of(credentials);
        when(userRepository.findCredentials(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(new RegisterDTO("jane.doe@example.org", "iloveyou", "Role")));
        verify(userRepository).findCredentials(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#createUser(RegisterDTO)}
     */
    @Test
    void testCreateUser2() {
        Optional<Credentials> emptyResult = Optional.empty();
        when(userRepository.findCredentials(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(new RegisterDTO("jane.doe@example.org", "iloveyou", "Role")));
        verify(userRepository).findCredentials(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#createUser(RegisterDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "cal.projeteq3.glucose.dto.auth.RegisterDTO.getEmail()" because "registerDTO" is null
        //       at cal.projeteq3.glucose.service.UserService.createUser(UserService.java:61)
        //   See https://diff.blue/R013 to resolve this issue.

        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());
        Optional<Credentials> ofResult = Optional.of(credentials);
        when(userRepository.findCredentials(Mockito.<String>any())).thenReturn(ofResult);
        userService.createUser(null);
    }

    /**
     * Method under test: {@link UserService#createUser(RegisterDTO)}
     */
    @Test
    void testCreateUser4() {
        when(userRepository.findCredentials(Mockito.<String>any()))
                .thenThrow(new UserNotFoundException("jane.doe@example.org"));
        assertThrows(UserNotFoundException.class,
                () -> userService.createUser(new RegisterDTO("jane.doe@example.org", "iloveyou", "Role")));
        verify(userRepository).findCredentials(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#createUser(RegisterDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.hashCode()" because "<local3>" is null
        //       at cal.projeteq3.glucose.service.UserService.createUser(UserService.java:63)
        //   See https://diff.blue/R013 to resolve this issue.

        Optional<Credentials> emptyResult = Optional.empty();
        when(userRepository.findCredentials(Mockito.<String>any())).thenReturn(emptyResult);
        userService.createUser(new RegisterDTO());
    }


}

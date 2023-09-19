package cal.projeteq3.glucose.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.model.user.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private ManagerService managerService;

    private Student student;
    private Employer employer;
    private Manager manager;

    @BeforeAll
    static void setUp() {


    }

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    void testAuthenticateUser() {
        assertThrows(UserNotFoundException.class,
                () -> userService.authenticateUser(new LoginDTO("jane.doe@example.org", "iloveyou")));
    }

    /**
     * Method under test: {@link UserService#authenticateUser(LoginDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAuthenticateUser2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "cal.projeteq3.glucose.dto.auth.LoginDTO.getEmail()" because "loginDTO" is null
        //       at cal.projeteq3.glucose.service.UserService.authenticateUser(UserService.java:43)
        //   See https://diff.blue/R013 to resolve this issue.

        userService.authenticateUser(null);
    }
}


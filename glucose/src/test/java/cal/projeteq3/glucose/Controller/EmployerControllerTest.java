package cal.projeteq3.glucose.Controller;

import cal.projeteq3.glucose.controller.EmployerController;
import cal.projeteq3.glucose.dto.EmployerDTO;
import cal.projeteq3.glucose.model.Employer;
import cal.projeteq3.glucose.service.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployerControllerTest {
    private EmployerController employerController;
    private EmployerService employerService;

    @BeforeEach
    void setUp() {
        employerService = mock(EmployerService.class);
        employerController = new EmployerController(employerService);
    }

    @Test
    void Register_Valid_Employe() {
        // Arrange
        Employer validEmployer = new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        when(employerService.createEmployer(validEmployer)).thenReturn(new EmployerDTO("organisationName", "organisationPhone"));

        // Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(validEmployer);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void Register_Missing_FirstName(){
//        Arrange
        Employer invalidEmployer = new Employer(
                "",
                "Michaud",
                "test@test.com",
                "Ose12asd3",
                "Fritz",
                "111-111-1111",
                null);

//        Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Missing_LastName(){
//        Arrange
        Employer invalidEmployer = new Employer(
                "Michel",
                "",
                "test@test.com",
                "Ose12asd3",
                "Fritz",
                "111-111-1111",
                null);

//        Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Missing_Email(){
//        Arrange
        Employer invalidEmployer = new Employer(
                "Michel",
                "Michaud",
                "",
                "Ose12asd3",
                "Fritz",
                "111-111-1111",
                null);

//        Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Missing_Password(){
//        Arrange
        Employer invalidEmployer = new Employer(
                "Michel",
                "Michaud",
                "test@test.com",
                "",
                "Fritz",
                "111-111-1111",
                null);

//        Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Missing_OrganisationName(){
//        Arrange
        Employer invalidEmployer = new Employer(
                "Michel",
                "Michaud",
                "test@test.com",
                "Ose12asd3",
                "",
                "111-111-1111",
                null);

//        Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Missing_OrganisationPhone(){
//        Arrange
        Employer invalidEmployer = new Employer(
                "Michel",
                "Michaud",
                "test@test.com",
                "Ose12asd3",
                "Fritz",
                "",
                null);

//        Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Invalid_Email() {
        // Arrange
        EmployerService employerService = mock(EmployerService.class);
        Employer invalidEmployer = new Employer(
                "John",
                "Doe",
                "invalid-email",
                "Passw0rd",
                "OrgName",
                "123-456-7890",
                null);
        EmployerController employerController = new EmployerController(employerService);

        // Act
        ResponseEntity<EmployerDTO> response = employerController.addEmployeur(invalidEmployer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void Register_Invalid_Password() {
        // Arrange
        EmployerService employerService = mock(EmployerService.class);
        Employer invalidEmployer = new Employer("John",
                "Doe",
                "john@example.com",
                "password",
                "OrgName",
                "123-456-7890",
                null);
        EmployerController employerController = new EmployerController(employerService);

        // Act
        ResponseEntity<EmployerDTO> response = employerController.addEmployeur(invalidEmployer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void Register_Invalid_FirstName() {
        // Arrange
        EmployerService employerService = mock(EmployerService.class);
        Employer invalidEmployer = new Employer(
                "InvalidName@123",
                "Doe",
                "john@example.com",
                "Passw0rd",
                "OrgName",
                "123-456-7890",
                null
        );
        EmployerController employerController = new EmployerController(employerService);

        // Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Invalid_LastName() {
        // Arrange
        EmployerService employerService = mock(EmployerService.class);
        Employer invalidEmployer = new Employer(
                "Michel",
                "InvalidName@123",
                "john@example.com",
                "Passw0rd",
                "OrgName",
                "123-456-7890",
                null
        );
        EmployerController employerController = new EmployerController(employerService);

        // Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Invalid_OrganisationName() {
        // Arrange
        EmployerService employerService = mock(EmployerService.class);
        Employer invalidEmployer = new Employer(
                "John",
                "Doe",
                "john@example.com",
                "Passw0rd",
                "Invalid Organization Name 123@",
                "123-456-7890",
                null
        );
        EmployerController employerController = new EmployerController(employerService);

        // Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Invalid_PhoneNumber() {
        // Arrange
        EmployerService employerService = mock(EmployerService.class);
        Employer invalidEmployer = new Employer(
                "John",
                "Doe",
                "john@example.com",
                "Passw0rd",
                "OrgName",
                "InvalidPhoneNumber",
                null
        );
        EmployerController employerController = new EmployerController(employerService);

        // Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(invalidEmployer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}

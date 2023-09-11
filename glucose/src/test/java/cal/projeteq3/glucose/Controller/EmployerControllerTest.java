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
    void AddAValidEmploye() {
        // Arrange
        Employer validEmployer = new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        when(employerService.createEmployer(validEmployer)).thenReturn(new EmployerDTO("organisationName", "organisationPhone"));

        // Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.addEmployeur(validEmployer);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}

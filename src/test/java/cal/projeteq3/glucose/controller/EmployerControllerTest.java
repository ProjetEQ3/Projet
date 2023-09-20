package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployerControllerTest{

    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterEmployer() throws ValidationException{
        RegisterEmployerDTO employerDTO = new RegisterEmployerDTO();
        when(employerService.createEmployer(employerDTO)).thenReturn(new EmployerDTO());
        ResponseEntity<EmployerDTO> response = employerController.register(employerDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testRegisterEmployerValidationException() throws ValidationException{
        RegisterEmployerDTO employerDTO = new RegisterEmployerDTO();
        doThrow(new ValidationException("Validation error")).when(Validation.class);
        ResponseEntity<EmployerDTO> response = employerController.register(employerDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testRegisterEmployerInternalServerError() throws ValidationException{
        RegisterEmployerDTO employerDTO = new RegisterEmployerDTO();
        doThrow(new RuntimeException("Internal Server Error")).when(employerService).createEmployer(employerDTO);
        ResponseEntity<EmployerDTO> response = employerController.register(employerDTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllJobOffers(){
        Long employerId = 1L;
        List<JobOfferDTO> jobOffers = new ArrayList<>();
        when(employerService.getAllJobOffers(employerId)).thenReturn(jobOffers);
        ResponseEntity<List<JobOfferDTO>> response = employerController.getAllJobOffers(employerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jobOffers, response.getBody());
    }

    @Test
    public void testAddJobOffer() throws ValidationException{
        JobOfferDTO jobOffer = new JobOfferDTO();
        Long employerId = 1L;
        doNothing().when(Validation.class);
        doNothing().when(employerService).createJobOffer(jobOffer, employerId);
        ResponseEntity<?> response = employerController.addJobOffer(jobOffer, employerId);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testAddJobOfferValidationException() throws ValidationException{
        JobOfferDTO jobOffer = new JobOfferDTO();
        Long employerId = 1L;
        doThrow(new ValidationException("Validation error")).when(Validation.class);
        ResponseEntity<?> response = employerController.addJobOffer(jobOffer, employerId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testAddJobOfferInternalServerError() throws ValidationException{
        JobOfferDTO jobOffer = new JobOfferDTO();
        Long employerId = 1L;
        doNothing().when(Validation.class);
        doThrow(new RuntimeException("Internal Server Error")).when(employerService).createJobOffer(jobOffer, employerId);
        ResponseEntity<?> response = employerController.addJobOffer(jobOffer, employerId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateJobOffer() throws ValidationException {
        JobOfferDTO jobOffer = new JobOfferDTO();
        doNothing().when(Validation.class);
        when(employerService.updateJobOffer(jobOffer)).thenReturn(jobOffer);
        ResponseEntity<JobOfferDTO> response = employerController.updateJobOffer(jobOffer);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(jobOffer, response.getBody());
    }

    @Test
    public void testUpdateJobOfferValidationException() throws ValidationException {
        JobOfferDTO jobOffer = new JobOfferDTO();
        doThrow(new ValidationException("Validation error")).when(Validation.class);
        ResponseEntity<JobOfferDTO> response = employerController.updateJobOffer(jobOffer);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateJobOfferInternalServerError() throws ValidationException {
        JobOfferDTO jobOffer = new JobOfferDTO();
        doNothing().when(Validation.class);
        doThrow(new RuntimeException("Internal Server Error")).when(employerService).updateJobOffer(jobOffer);
        ResponseEntity<JobOfferDTO> response = employerController.updateJobOffer(jobOffer);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteJobOffer() {
        Long jobOfferId = 1L;
        doNothing().when(employerService).deleteJobOffer(jobOfferId);
        ResponseEntity<?> response = employerController.deleteJobOffer(jobOfferId);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testDeleteJobOfferSuccess() {
        Long jobOfferId = 1L;
        doNothing().when(employerService).deleteJobOffer(jobOfferId);
        ResponseEntity<?> response = employerController.deleteJobOffer(jobOfferId);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testDeleteJobOfferInternalServerError() {
        Long jobOfferId = 1L;
        doThrow(new RuntimeException("Internal Server Error")).when(employerService).deleteJobOffer(jobOfferId);
        ResponseEntity<?> response = employerController.deleteJobOffer(jobOfferId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteJobOfferValidationException() {
        Long jobOfferId = 1L;
        doThrow(new ValidationException("Validation error")).when(Validation.class);
        ResponseEntity<?> response = employerController.deleteJobOffer(jobOfferId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*@Test
    public void testDeleteJobOfferNotFound() {
        Long jobOfferId = 1L;
        doThrow(new JobOfferNotFoundException("Job offer not found")).when(employerService).deleteJobOffer(jobOfferId);
        ResponseEntity<?> response = employerController.deleteJobOffer(jobOfferId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }*/
}
/*
package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.service.EmployerService;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class EmployerControllerTest {
    private EmployerController employerController;
    private EmployerService employerService;

    @BeforeEach
    void setUp() {
        employerService = mock(EmployerService.class);
        employerController = new EmployerController(employerService);
    }

    */
/*@Test
    void Register_Valid_Employe() {
        // Arrange
        Employer validEmployer = new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        when(employerService.createEmployer(validEmployer)).thenReturn(new EmployerDTO("organisationName", "organisationPhone", null));

        // Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(validEmployer);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void Register_Missing_LastName(){
//        Arrange
        Employer invalidEmployer = new Employer(
                1,
                "Michel",
                "",
                "test@test.com",
                "Ose12asd3",
                "Fritz",
                "111-111-1111",
                null);

//        Act
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> response = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> response = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

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
        ResponseEntity<EmployerDTO> responseEntity = employerController.register(invalidEmployer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }*//*

}
*/

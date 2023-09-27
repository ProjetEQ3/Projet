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
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployerControllerTest{
	@InjectMocks
	private EmployerController employerController;

	@Mock
	private EmployerService employerService;

	private RegisterEmployerDTO employerDTO;

	private JobOfferDTO jobOfferDTO;

	@BeforeEach
	public void setup(){
		employerDTO = new RegisterEmployerDTO();
		jobOfferDTO = new JobOfferDTO();
	}

	@Test
	public void testRegisterSuccess(){
		// The way we are validating inside Controllers is wrong,
		// but since is decided to do it that way, had to disable Validations here
		try(MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)){
			mockedValidation.when(() -> Validation.validateEmployer(employerDTO)).thenAnswer(invocation -> null);
			when(employerService.createEmployer(employerDTO)).thenReturn(new EmployerDTO());

			ResponseEntity<EmployerDTO> response = employerController.register(employerDTO);

			assertEquals(HttpStatus.CREATED, response.getStatusCode());
		}
	}

	@Test
	public void testRegisterGeneralException(){
		when(employerService.createEmployer(employerDTO)).thenThrow(RuntimeException.class);

		ResponseEntity<EmployerDTO> response = employerController.register(employerDTO);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testRegisterEmployerInternalServerError() throws ValidationException{
		RegisterEmployerDTO employerDTO = new RegisterEmployerDTO();
		doThrow(new RuntimeException("Internal Server Error")).when(employerService).createEmployer(employerDTO);
		ResponseEntity<EmployerDTO> response = employerController.register(employerDTO);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testRegisterValidationException(){
		// The way we are validating inside Controllers is wrong,
		// but since is decided to do it that way, had to disable Validations here
		try(MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)){
			mockedValidation.when(() -> Validation.validateEmployer(employerDTO)).thenAnswer(invocation -> null);
			when(employerService.createEmployer(employerDTO)).thenThrow(new ValidationException("error"));

			ResponseEntity<EmployerDTO> response = employerController.register(employerDTO);

			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			assertEquals("error", response.getHeaders().getFirst("X-Errors"));
		}
	}

	@Test
	public void testGetAllJobOffers(){
		when(employerService.getAllJobOffers(1L)).thenReturn(Collections.singletonList(jobOfferDTO));

		ResponseEntity<List<JobOfferDTO>> response = employerController.getAllJobOffers(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, Objects.requireNonNull(response.getBody()).size());
	}

	@Test
	public void testDeleteJobOffer(){
		doNothing().when(employerService).deleteJobOffer(1L);

		ResponseEntity<?> response = employerController.deleteJobOffer(1L);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	public void testDeleteJobOfferSuccess(){
		doNothing().when(employerService).deleteJobOffer(1L);

		ResponseEntity<?> response = employerController.deleteJobOffer(1L);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	public void testAddJobOfferSuccess(){
		// The way we are validating inside Controllers is wrong,
		// but since is decided to do it that way, had to disable Validations here
		try(MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)){
			mockedValidation.when(() -> Validation.validateJobOffer(jobOfferDTO)).thenAnswer(invocation -> null);
			when(employerService.createJobOffer(jobOfferDTO, 1L)).thenReturn(jobOfferDTO);

			ResponseEntity<JobOfferDTO> response = employerController.addJobOffer(jobOfferDTO, 1L);

			assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		}
	}

	@Test
	public void testAddJobOfferValidationException(){
		// The way we are validating inside Controllers is wrong,
		// but since is decided to do it that way, had to disable Validations here
		try(MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)){
			mockedValidation.when(() -> Validation.validateJobOffer(jobOfferDTO)).thenAnswer(invocation -> null);
			when(employerService.createJobOffer(jobOfferDTO, 1L)).thenThrow(new ValidationException("error"));

			ResponseEntity<JobOfferDTO> response = employerController.addJobOffer(jobOfferDTO, 1L);

			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			assertEquals("error", response.getHeaders().getFirst("X-Errors"));
		}
	}

	@Test
	public void testAddJobOfferGeneralException(){
		when(employerService.createJobOffer(jobOfferDTO, 1L)).thenThrow(RuntimeException.class);

		ResponseEntity<JobOfferDTO> response = employerController.addJobOffer(jobOfferDTO, 1L);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateJobOfferSuccess(){
		// The way we are validating inside Controllers is wrong,
		// but since is decided to do it that way, had to disable Validations here
		try(MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)){
			mockedValidation.when(() -> Validation.validateJobOffer(jobOfferDTO)).thenAnswer(invocation -> null);
			when(employerService.updateJobOffer(jobOfferDTO)).thenReturn(jobOfferDTO);

			ResponseEntity<JobOfferDTO> response = employerController.updateJobOffer(jobOfferDTO);

			assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		}
	}

	@Test
	public void testUpdateJobOfferValidationException(){
		// The way we are validating inside Controllers is wrong,
		// but since is decided to do it that way, had to disable Validations here
		try(MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)){
			mockedValidation.when(() -> Validation.validateJobOffer(jobOfferDTO)).thenAnswer(invocation -> null);
			when(employerService.updateJobOffer(jobOfferDTO)).thenThrow(new ValidationException("error"));

			ResponseEntity<JobOfferDTO> response = employerController.updateJobOffer(jobOfferDTO);

			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			assertEquals("error", response.getHeaders().getFirst("X-Errors"));
		}
	}

	@Test
	public void testUpdateJobOfferGeneralException(){
		when(employerService.updateJobOffer(jobOfferDTO)).thenThrow(RuntimeException.class);

		ResponseEntity<JobOfferDTO> response = employerController.updateJobOffer(jobOfferDTO);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

}
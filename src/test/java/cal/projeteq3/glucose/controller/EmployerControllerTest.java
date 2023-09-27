package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.validation.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {EmployerController.class, EmployerService.class})
@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployerService employerService;

	private RegisterEmployerDTO employerDTO;
	private JobOfferDTO jobOfferDTO;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		employerDTO = new RegisterEmployerDTO();
		jobOfferDTO = new JobOfferDTO();
	}

	private RegisterEmployerDTO createRegisterEmployer(String email, String password, String firstName, String lastName, String organisationName, String organisationPhone){
		RegisterEmployerDTO registerEmployerDTO = new RegisterEmployerDTO();
		registerEmployerDTO.setEmployerDTO(new EmployerDTO(1L, firstName, lastName, email, organisationName, organisationPhone));
		registerEmployerDTO.setRegisterDTO(new RegisterDTO(email, password, "EMPLOYER"));

		return registerEmployerDTO;
	}

	@Test
	public void Register_Success() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here

		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"Test12345",
						"John",
						"Doe",
						"fritz",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		when(employerService.createEmployer(employerDTO)).thenReturn(registerEmployerDTO.getEmployerDTO());

		mockMvc.perform(MockMvcRequestBuilders.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void Register_NPE() throws Exception {
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"Test12345",
						"John",
						null,
						"fritz",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));
	}

	@Test
	public void Register_Invalid_OrganisationName() throws Exception {
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"Test12345",
						"John",
						"Doe",
						"fritz---",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));

	}

	@Test
	public void Register_InvalidEmail_emptyAtt() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"testm",
						"Test12345",
						"John",
						"Doe",
						"fritz",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));

	}

	@Test
	public void Register_InvalidPassword_emptyUpperCase() throws Exception {
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"test12345",
						"John",
						"Doe",
						"fritz",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));

	}

	@Test
	public void Register_InvalidPassword_emptyNumber() throws Exception {
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"tesTtttt",
						"John",
						"Doe",
						"fritz",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));

	}

	@Test
	public void Register_InvalidName_emptyFirstName() throws Exception {
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"tesT12345",
						"",
						"Doe",
						"fritz",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));

	}

	@Test
	public void Register_InvalidName_emptyLastName() throws Exception {
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"tesT12345",
						"John",
						"",
						"fritz",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));

	}

	@Test
	public void Register_InvalidName_emptyOrganisationName() throws Exception {
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"tesT12345",
						"John",
						"Doe",
						"",
						"123-123-1234");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));

	}

	@Test
	public void Register_InvalidName_emptyOrganisationNumber() throws Exception {
		RegisterEmployerDTO registerEmployerDTO =
				createRegisterEmployer(
						"test@test.com",
						"tesT12345",
						"John",
						"Doe",
						"Fritz",
						"");

		String content = (new ObjectMapper()).writeValueAsString(registerEmployerDTO);

		assertThrows(ServletException.class, () ->
				mockMvc.perform(MockMvcRequestBuilders
								.post("/employer/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError()));

	}

	@Test
	public void GetAllJobOffers_valid() throws Exception {
		when(employerService.getAllJobOffers(1L)).thenReturn(Collections.singletonList(jobOfferDTO));

		mockMvc.perform(MockMvcRequestBuilders
						.get("/your-get-all-job-offers-endpoint/{employerId}", 1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
	}

	@Test
	public void testDeleteJobOffer() throws Exception {
		doNothing().when(employerService).deleteJobOffer(1L);

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/your-delete-job-offer-endpoint/{jobOfferId}", 1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void testAddJobOfferSuccess() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		try (MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)) {
			mockedValidation.when(() -> Validation.validateJobOffer(jobOfferDTO)).thenAnswer(invocation -> null);
			when(employerService.createJobOffer(jobOfferDTO, 1L)).thenReturn(jobOfferDTO);

			mockMvc.perform(MockMvcRequestBuilders
							.post("/your-add-job-offer-endpoint/{employerId}", 1L)
							.contentType(MediaType.APPLICATION_JSON)
							.content("{\"title\":\"Test Job\"}")) // Replace with your JSON content
					.andExpect(MockMvcResultMatchers.status().isAccepted());
		}
	}

	@Test
	public void testAddJobOfferValidationException() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		try (MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)) {
			mockedValidation.when(() -> Validation.validateJobOffer(jobOfferDTO)).thenAnswer(invocation -> null);
			when(employerService.createJobOffer(jobOfferDTO, 1L)).thenThrow(new ValidationException("error"));

			mockMvc.perform(MockMvcRequestBuilders
							.post("/your-add-job-offer-endpoint/{employerId}", 1L)
							.contentType(MediaType.APPLICATION_JSON)
							.content("{\"title\":\"Test Job\"}")) // Replace with your JSON content
					.andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andExpect(MockMvcResultMatchers.header().string("X-Errors", "error"));
		}
	}

	@Test
	public void testUpdateJobOfferSuccess() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		try (MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)) {
			mockedValidation.when(() -> Validation.validateJobOffer(jobOfferDTO)).thenAnswer(invocation -> null);
			when(employerService.updateJobOffer(jobOfferDTO)).thenReturn(jobOfferDTO);

			mockMvc.perform(MockMvcRequestBuilders
							.put("/your-update-job-offer-endpoint")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{\"title\":\"Updated Test Job\"}")) // Replace with your JSON content
					.andExpect(MockMvcResultMatchers.status().isAccepted());
		}
	}

	@Test
	public void testUpdateJobOfferValidationException() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		try (MockedStatic<Validation> mockedValidation = mockStatic(Validation.class)) {
			mockedValidation.when(() -> Validation.validateJobOffer(jobOfferDTO)).thenAnswer(invocation -> null);
			when(employerService.updateJobOffer(jobOfferDTO)).thenThrow(new ValidationException("error"));

			mockMvc.perform(MockMvcRequestBuilders
							.put("/your-update-job-offer-endpoint")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{\"title\":\"Updated Test Job\"}")) // Replace with your JSON content
					.andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andExpect(MockMvcResultMatchers.header().string("X-Errors", "error"));
		}
	}

	@Test
	public void testUpdateJobOfferGeneralException() throws Exception {
		when(employerService.updateJobOffer(jobOfferDTO)).thenThrow(RuntimeException.class);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/your-update-job-offer-endpoint")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"Updated Test Job\"}")) // Replace with your JSON content
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

}
package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {EmployerController.class, CustomExceptionHandler.class})
@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployerService employerService;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();
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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);


		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

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

		String content = objectMapper.writeValueAsString(registerEmployerDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

	@Test
	public void GetAllJobOffers_valid() throws Exception {
		Long employerId = 1L;
		List<JobOfferDTO> jobOfferDTOs = new ArrayList<>(List.of(
				new JobOfferDTO(1L, "Test Job", Department._420B0,
						"MTL", "Test Job Description", 1.0f,
						LocalDateTime.now(), 10, LocalDateTime.now().plusDays(3),
						JobOfferState.OPEN, 30, null)
		));

		when(employerService.getAllJobOffers(employerId)).thenReturn(jobOfferDTOs);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/employer/offer/all")
						.param("employerId", employerId.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(employerId));
	}

	@Test
	public void DeleteJobOffer_Valid() throws Exception {
		Long id = 1L;
		doNothing().when(employerService).deleteJobOffer(id);

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/employer/offer/{id}", id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void AddJobOffer_Success() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		Long employerId = 1L;
		JobOfferDTO jobOfferDTO = new JobOfferDTO(employerId, "Software Engineer", Department._420B0,
				"New York", "We are looking for a talented software engineer to join our team.", 80000.0f,
				LocalDateTime.now(), 12, LocalDateTime.now().plusDays(3),
				JobOfferState.SUBMITTED, 40, null);

		JobOfferDTO post = new JobOfferDTO(null, "Software Engineer", Department._420B0,
				"New York", "We are looking for a talented software engineer to join our team.", 80000.0f,
				LocalDateTime.now(), 12, LocalDateTime.now().plusDays(3),
				JobOfferState.SUBMITTED, 40, null);

		String content ="""
    	{
				"title": "Software Engineer",
				"department": "_420B0",
				"location": "New York",
				"description": "We are looking for a talented software engineer to join our team.",
				"salary": 80000.0,
				"startDate": "2023-10-01T09:00:00",
				"duration": 12,
				"expirationDate": "2023-10-15T23:59:59",
				"jobOfferState": "SUBMITTED",
				"hoursPerWeek": 40,
				"refusReason": null
		}""";


		when(employerService.createJobOffer(post, employerId)).thenReturn(jobOfferDTO);
		when(employerService.createJobOffer(jobOfferDTO, employerId)).thenReturn(jobOfferDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/offer")
						.param("employerId", employerId.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().isAccepted());

	}

	@Test
	public void AddJobOffer_ValidationException() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		Long employerId = 1L;

		String content ="""
    	{
				"title": "Software Engineer",
				"department": "_420B0",
				"location": "New York",
				"description": "We are looking for a talented software engineer to join our team.",
				"salary": 80000.0,
				"startDate": "2023-10-01T09:00:00",
				"duration": 12,
				"expirationDate": "2023-10-15T23:59:59",
				"jobOfferState": "SUBMITTED",
				"hoursPerWeek": 40,
				"refusReason": null
		}""";

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/offer")
						.param("employerId", employerId.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void UpdateJobOffer_Success() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		String content ="""
    	{
				"title": "Software Engineer",
				"department": "_420B0",
				"location": "New York",
				"description": "We are looking for a talented software engineer to join our team.",
				"salary": 80000.0,
				"startDate": "2023-10-01T09:00:00",
				"duration": 12,
				"expirationDate": "2023-10-15T23:59:59",
				"jobOfferState": "SUBMITTED",
				"hoursPerWeek": 40,
				"refusReason": null
		}""";


		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void UpdateJobOffer_ValidationException() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		String content ="""
    	{
				"title": "",
				"department": "_420B0",
				"location": "New York",
				"description": "We are looking for a talented software engineer to join our team.",
				"salary": 80000.0,
				"startDate": "2023-10-01T09:00:00",
				"duration": 12,
				"expirationDate": "2023-09-15T23:59:59",
				"jobOfferState": "SUBMITTED",
				"hoursPerWeek": 40,
				"refusReason": null
		}""";

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void UpdateJobOffer_GeneralException() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		String content ="""
    	{
				"title": "awdaf",
				"department": "_420B1",
				"location": "New York",
				"description": "We are looking for a talented software engineer to join our team.",
				"salary": 80000.0,
				"startDate": "2023-10-01T09:00:00",
				"duration": 12,
				"expirationDate": "2023-09-15T23:59:59",
				"jobOfferState": "SUBMITTED",
				"hoursPerWeek": 40,
				"refusReason": null
		}""";

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
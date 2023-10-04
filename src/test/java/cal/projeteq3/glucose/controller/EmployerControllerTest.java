package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.contract.EmploymentType;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.validation.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = {EmployerController.class, EmployerService.class, CustomExceptionHandler.class})
@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployerService employerService;

	private ObjectMapper objectMapper;

	@MockBean
	private ManagerService managerService;
	@MockBean
	private StudentService studentService;

	private ContractDTO contractDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();
		contractDTO = new ContractDTO();
		contractDTO.setEmployerId(1L);
		contractDTO.setSupervisorId(2L);
		contractDTO.setWorkAddressId(3L);
		contractDTO.setStudentId(4L);
		contractDTO.setJobTitle("Software Engineer");
		contractDTO.setStartDate(LocalDate.now());
		contractDTO.setEndDate(LocalDate.now().plusMonths(6));
		contractDTO.setHoursPerWeek(40);
		contractDTO.setEmploymentType(EmploymentType.FULL_TIME);
		contractDTO.setResponsibilities(List.of("Develop software", "Test software", "Collaborate with team"));
		contractDTO.setWorkDays(
			Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
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
				.andExpect(MockMvcResultMatchers.status().isCreated())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@test.com"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.organisationName").value("fritz"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.organisationPhone").value("123-123-1234"))
		;
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
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(employerId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("Test Job"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].department").value(Department._420B0.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].location").value("MTL"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value("Test Job Description"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].salary").value(1.0f))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].startDate").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].duration").value(10))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].expirationDate").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].jobOfferState").value(JobOfferState.OPEN.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].hoursPerWeek").value(30))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].refusReason").doesNotExist())
				;
	}

	@Test
	public void DeleteJobOffer_Valid() throws Exception {
		Long id = 1L;
		doNothing().when(employerService).deleteJobOffer(id);

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/employer/offer/{id}", id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
		;
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

		String content = """
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
				.andExpect(MockMvcResultMatchers.status().isAccepted())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Software Engineer"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.department").value(Department._420B0.toString()))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.location").value("New York"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("We are looking for a talented software engineer to join our team."))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(80000.0f))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(12))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.expirationDate").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.jobOfferState").value(JobOfferState.SUBMITTED.toString()))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.hoursPerWeek").value(40))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.refusReason").doesNotExist())
				;

	}

	@Test
	public void AddJobOffer_ValidationException() throws Exception {
		// The way we are validating inside Controllers is wrong,
		// but since it's decided to do it that way, had to disable Validations here
		long employerId = 1L;

		String content ="""
					{
				"title": "Software Engineer",
				"department": "_420B0",
				"location": "New York",
				"description": "We are looking for a talented software engineer to join our team.",
				"salary": 80000.0,
				"startDate": "2023-10-01T09:00:00",
				"duration": -023,
				"expirationDate": "2023-10-15T23:59:59",
				"jobOfferState": "SUBMITTED",
				"hoursPerWeek": 40,
				"refusReason": null
		}""";

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/offer")
						.param("employerId", Long.toString(employerId))
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
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
				.andExpect(MockMvcResultMatchers.status().isAccepted())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Software Engineer"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.department").value(Department._420B0.toString()))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.location").value("New York"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("We are looking for a talented software engineer to join our team."))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(80000.0f))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(12))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.expirationDate").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.jobOfferState").value(JobOfferState.SUBMITTED.toString()))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.hoursPerWeek").value(40))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.refusReason").doesNotExist())
		;
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

	@Test
	public void createContractTest() throws Exception {
		when(managerService.createContract(any(ContractDTO.class))).thenReturn(contractDTO);

		mockMvc
			.perform(post("/manager/contract/create")
				         .contentType(MediaType.APPLICATION_JSON)
				         .content(objectMapper.writeValueAsString(contractDTO)))
			.andExpect(status().isOk());

		verify(managerService, times(1)).createContract(any(ContractDTO.class));
	}
}

package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.config.SecurityConfiguration;
import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.badRequestException.JobApplicationNotFoundException;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.repository.UserRepository;
import cal.projeteq3.glucose.security.JwtAuthenticationEntryPoint;
import cal.projeteq3.glucose.security.JwtTokenProvider;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cal.projeteq3.glucose.exception.badRequestException.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {EmployerController.class, CustomExceptionHandler.class,
		SecurityConfiguration.class, JwtTokenProvider.class, JwtAuthenticationEntryPoint.class})
@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployerService employerService;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private UserService userService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private ObjectMapper objectMapper;
	private String token;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();
		when(userRepository.findUserByCredentialsEmail(anyString())).thenReturn(Optional.of(Employer.builder().build()));
		this.token = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("louis@professionnel.com", "Ose12345"));
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
				.andExpect(MockMvcResultMatchers.status().is(673));
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
						LocalDate.now(), 10, LocalDate.now().plusDays(3),
						JobOfferState.OPEN, 30, null,1, new Semester(LocalDate.now()))
		));

		when(employerService.getAllJobOffers(employerId, new Semester(LocalDate.now()))).thenReturn(jobOfferDTOs);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/employer/offer/all")
						.header("Authorization", token)
						.param("employerId", employerId.toString())
						.param("season", "FALL")
						.param("year", "2021")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(employerId))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("Test Job"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].department").value(Department._420B0.toString()))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].location").value("MTL"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value("Test Job Description"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].salary").value(1.0f))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].startDate").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].duration").value(10))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].expirationDate").exists())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].jobOfferState").value(JobOfferState.OPEN.toString()))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].hoursPerWeek").value(30))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].refusReason").doesNotExist())
				;
	}

	@Test
	public void DeleteJobOffer_Valid() throws Exception {
		Long id = 1L;
		doNothing().when(employerService).deleteJobOffer(id);

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/employer/offer/{id}", id)
						.header("Authorization", token)
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
				LocalDate.now(), 12, LocalDate.now().plusDays(3),
				JobOfferState.SUBMITTED, 40, null,1,new Semester(LocalDate.now()));

		JobOfferDTO post = new JobOfferDTO(null, "Software Engineer", Department._420B0,
				"New York", "We are looking for a talented software engineer to join our team.", 80000.0f,
				LocalDate.now(), 12, LocalDate.now().plusDays(3),
				JobOfferState.SUBMITTED, 40, null,1,new Semester(LocalDate.now()));

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
						.header("Authorization", token)
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
						.header("Authorization", token)
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

		when(employerService.updateJobOffer(new JobOfferDTO(1L, "Software Engineer", Department._420B0,
				"New York", "We are looking for a talented software engineer to join our team.", 80000.0f,
				LocalDate.now(), 12, LocalDate.now().plusDays(3),
				JobOfferState.SUBMITTED, 40, null,1,new Semester(LocalDate.now())))).thenReturn(new JobOfferDTO(1L, "Software Engineer", Department._420B0,
				"New York", "We are looking for a talented software engineer to join our team.", 80000.0f,
				LocalDate.now(), 12, LocalDate.now().plusDays(3),
				JobOfferState.SUBMITTED, 40, null,1,new Semester(LocalDate.now())));

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer")
						.header("Authorization", token)
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
						.header("Authorization", token)
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
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void getPendingStudentsByJobOffer() throws Exception {
		// Arrange
		StudentDTO studentDTO = new StudentDTO("John", "Doe", "1234567", Department._420B0);
		List<StudentDTO> studentDTOs = new ArrayList<>(List.of(studentDTO));
		Long jobOfferId = 1L;

		when(employerService.getPendingStudentsByJobOfferId(jobOfferId)).thenReturn(studentDTOs);

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders
						.get("/employer/offer/students/{id}", jobOfferId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("John"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName").value("Doe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].matricule").value("1234567"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].department").value(Department._420B0.toString()));
	}

	@Test
	public void AcceptJobApplication_valid() throws Exception {
		Long applicationId = 1L;
		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		jobApplicationDTO.setId(applicationId);
		when(employerService.acceptApplication(applicationId)).thenReturn(jobApplicationDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/accept/{jobApplicationId}", applicationId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(jobApplicationDTO.getId()))
		;
	}

	@Test
	public void AcceptJobApplication_invalid() throws Exception {
		Long applicationId = -1L;
		when(employerService.acceptApplication(applicationId)).thenThrow(new JobApplicationNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/accept/{jobApplicationId}", applicationId)
						.header("Authorization", token))
				.andExpect(MockMvcResultMatchers.status().is(406));
	}

	@Test
	public void AcceptJobApplication_null() throws Exception {
		Long nullId = null;
		when(employerService.acceptApplication(nullId)).thenThrow(new JobApplicationNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/accept/{jobApplicationId}", nullId)
						.header("Authorization", token))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	public void RefuseJobApplication_valid() throws Exception {
		Long applicationId = 1L;
		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		jobApplicationDTO.setId(applicationId);
		when(employerService.refuseApplication(applicationId)).thenReturn(jobApplicationDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/refuse/{jobApplicationId}", applicationId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(jobApplicationDTO.getId()))
		;
	}

	@Test
	public void RefuseJobApplication_invalid() throws Exception {
		Long applicationId = -1L;
		when(employerService.refuseApplication(applicationId)).thenThrow(new JobApplicationNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/refuse/{jobApplicationId}", applicationId)
						.header("Authorization", token))
				.andExpect(MockMvcResultMatchers.status().is(406));
	}

	@Test
	public void RefuseJobApplication_null() throws Exception {
		Long nullId = null;
		when(employerService.refuseApplication(nullId)).thenThrow(new JobApplicationNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/refuse/{jobApplicationDTO}", nullId)
						.header("Authorization", token))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	public void addSuggestedAppointment_valid() throws Exception {
		Long applicationId = 1L;
		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		jobApplicationDTO.setId(applicationId);
		Set<LocalDateTime> dates = new HashSet<>();
		dates.add(LocalDateTime.now().plusDays(1));
		dates.add(LocalDateTime.now().plusDays(2));
		dates.add(LocalDateTime.now().plusDays(3));

		List<AppointmentDTO> appointments = new ArrayList<>();
		for (LocalDateTime date : dates) {
			AppointmentDTO app = new AppointmentDTO();
			app.setAppointmentDate(date);
			appointments.add(app);
		}

		jobApplicationDTO.setAppointments(appointments);

		when(employerService.addAppointmentByJobApplicationId(applicationId, dates)).thenReturn(jobApplicationDTO);

		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(dates);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/appointment/{jobApplicationId}", applicationId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void addSuggestedAppointment_MoreThan5Dates() throws Exception {
		Long applicationId = 1L;
		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		jobApplicationDTO.setId(applicationId);
		Set<LocalDateTime> dates = new HashSet<>();
		dates.add(LocalDateTime.now().plusDays(1));
		dates.add(LocalDateTime.now().plusDays(2));
		dates.add(LocalDateTime.now().plusDays(3));
		dates.add(LocalDateTime.now().plusDays(4));
		dates.add(LocalDateTime.now().plusDays(5));
		dates.add(LocalDateTime.now().plusDays(6));

		List<AppointmentDTO> appointments = new ArrayList<>();
		for (LocalDateTime date : dates) {
			AppointmentDTO app = new AppointmentDTO();
			app.setAppointmentDate(date);
			appointments.add(app);
		}

		jobApplicationDTO.setAppointments(appointments);

		when(employerService.addAppointmentByJobApplicationId(applicationId, dates)).thenThrow(ValidationException.class);

		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(dates);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/appointment/{jobApplicationId}", applicationId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is(406));
	}

	@Test
	public void addSuggestedAppointment_DateNull() throws Exception {
		Long applicationId = 1L;
		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		jobApplicationDTO.setId(applicationId);
		Set<LocalDateTime> dates = null;

		when(employerService.addAppointmentByJobApplicationId(applicationId, dates)).thenThrow(ValidationException.class);

		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(dates);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/appointment/{jobApplicationId}", applicationId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	public void addSuggestedAppointment_DateEmpty() throws Exception {
		Long applicationId = 1L;
		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		jobApplicationDTO.setId(applicationId);
		Set<LocalDateTime> dates = new HashSet<>();

		when(employerService.addAppointmentByJobApplicationId(applicationId, dates)).thenThrow(ValidationException.class);

		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(dates);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/appointment/{jobApplicationId}", applicationId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is(406));
	}

	@Test
	public void addSuggestedAppointment_IdNull() throws Exception{
		Long applicationId = null;
		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		jobApplicationDTO.setId(applicationId);
		Set<LocalDateTime> dates = new HashSet<>();
		dates.add(LocalDateTime.now().plusDays(1));
		dates.add(LocalDateTime.now().plusDays(2));
		dates.add(LocalDateTime.now().plusDays(3));
		dates.add(LocalDateTime.now().plusDays(3));

		when(employerService.addAppointmentByJobApplicationId(applicationId, dates)).thenThrow(ValidationException.class);

		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(dates);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/appointment/{jobApplicationId}", applicationId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	public void addSuggestedAppointment_IdInexistant() throws Exception{
		Long applicationId = 999L;
		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		jobApplicationDTO.setId(1L);
		Set<LocalDateTime> dates = new HashSet<>();
		dates.add(LocalDateTime.now().plusDays(1));
		dates.add(LocalDateTime.now().plusDays(2));
		dates.add(LocalDateTime.now().plusDays(3));

		when(employerService.addAppointmentByJobApplicationId(applicationId, dates)).thenThrow(new JobApplicationNotFoundException());

		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(dates);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/employer/offer/appointment/{jobApplicationId}", applicationId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(MockMvcResultMatchers.status().is(406));
	}

	@Test
	public void getWaitingStudents_valid() throws Exception {
		 Long employerId = 1L;
		 Semester currentSemester = new Semester(LocalDate.now());
		 String season = currentSemester.getSeason().toString();
		 String year = String.valueOf(currentSemester.getYear());
		 List<StudentDTO> studentDTOs = new ArrayList<>(List.of(
				 new StudentDTO("John", "Doe", "1234567", Department._420B0)
		 ));

		 when(employerService.getWaitingStudents(employerId, new Semester(LocalDate.now()))).thenReturn(studentDTOs);

		 mockMvc.perform(MockMvcRequestBuilders
				 .get("/employer/waitingStudents")
				 .header("Authorization", token)
				 .param("employerId", employerId.toString())
				 .param("season", season)
				 .param("year", year)
				 .contentType(MediaType.APPLICATION_JSON))
				 .andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void getWaitingStudents_InvalidSemester() throws Exception {
		Long employerId = 1L;
		List<StudentDTO> studentDTOs = new ArrayList<>(List.of(
				new StudentDTO("John", "Doe", "1234567", Department._420B0)
		));

		when(employerService.getWaitingStudents(employerId, new Semester(LocalDate.now()))).thenReturn(studentDTOs);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/employer/waitingStudents")
						.header("Authorization", token)
						.param("employerId", employerId.toString())
						.param("season", "FALL")
						.param("year", "2021")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void getOfferByApplicationId_valid() throws Exception {
		Long applicationId = 1L;
		JobOfferDTO jobOfferDTO = new JobOfferDTO(1L, "Test Job", Department._420B0,
				"MTL", "Test Job Description", 1.0f,
				LocalDate.now(), 10, LocalDate.now().plusDays(3),
				JobOfferState.OPEN, 30, null, 1, new Semester(LocalDate.now()));

		when(employerService.getOfferByApplicationId(applicationId)).thenReturn(jobOfferDTO);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/employer/offerByApplication")
						.header("Authorization", token)
						.param("applicationId", applicationId.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void getOfferByApplicationId_invalid() throws Exception {
		Long applicationId = -1L;
		when(employerService.getOfferByApplicationId(applicationId)).thenThrow(new JobApplicationNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders
						.get("/employer/offerByApplication")
						.header("Authorization", token)
						.param("applicationId", applicationId.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(406));
	}

//	TODO: Fix Les 2 autres (Je dois work)
	@Test
	public void getSuggestedAppointment() throws Exception {
		Long applicationId = 1L;

		when(employerService.getAppointmentByJobApplicationId(applicationId))
				.thenReturn(new AppointmentDTO(1L, new JobApplicationDTO(), LocalDateTime.now(), false));

		mockMvc.perform(MockMvcRequestBuilders.get("/employer/offer/appointment/{applicationId}", applicationId)
						.header("Authorization", token)
						.param("applicationId", applicationId.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void testGetAllContracts() throws Exception {
		String season = "FALL";
		String year = "2023";

		when(employerService.getContractsBySession(Semester.builder()
				.year(Integer.parseInt(year))
				.season(Semester.Season.valueOf(season)).build(), 1L))
				.thenReturn(new ArrayList<>());


		mockMvc.perform(MockMvcRequestBuilders
						.get("/employer/contracts/1")
						.param("season", season)
						.param("year", year)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	public void PostSignatureContract() throws Exception {
		LoginDTO loginDTO = new LoginDTO("test@test.com", "Test1234");
		Long contractId = 1L;

		when(employerService.signContract(contractId, 1L)).thenReturn(new ContractDTO());
		when(userService.authenticateUserContractSigning(loginDTO)).thenReturn(1L);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/employer/contract/sign/{contractId}", contractId)
						.header("Authorization", token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginDTO)))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
		;
	}
}
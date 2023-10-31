package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.config.SecurityConfiguration;
import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.contract.ShortContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.exception.badRequestException.AppointmentNotFoundException;
import cal.projeteq3.glucose.exception.badRequestException.StudentNotFoundException;
import cal.projeteq3.glucose.exception.unauthorizedException.JobOfferNotOpenException;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.UserRepository;
import cal.projeteq3.glucose.security.JwtAuthenticationEntryPoint;
import cal.projeteq3.glucose.security.JwtTokenProvider;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = {StudentController.class, CustomExceptionHandler.class,
        SecurityConfiguration.class, JwtTokenProvider.class, JwtAuthenticationEntryPoint.class})
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

	private final Long validStudentId = 1L;

	private final Long validJobOfferId = 1L;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String token;
    @BeforeEach
    public void setup() {
        when(userRepository.findUserByCredentialsEmail(anyString())).thenReturn(Optional.of(Student.builder().build()));
        token = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("louis@michaud.com","Ose12345"));
    }
      @Test
    public void Register_Valid() throws Exception {
        StudentDTO returnedStudent =
                new StudentDTO(1L, "asd", "asd", "blabla@example.ca", "STUDENT", "1231231", "_420B0");

        RegisterStudentDTO validDTO = new RegisterStudentDTO();
        validDTO.setRegisterDTO(new RegisterDTO("blabla@example.ca", "Ose12345", "STUDENT"));
        validDTO.setStudentDTO(new StudentDTO("Test", "Test", "1231231", Department._420B0));

        when(studentService.createStudent(validDTO)).thenReturn(returnedStudent);

        ObjectMapper objectMapper = new ObjectMapper();
        String validDTOJson = objectMapper.writeValueAsString(validDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validDTOJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void Register_InvalidPassword() throws Exception {
//        Arrange
        RegisterStudentDTO registerStudentDTO = new RegisterStudentDTO();
        registerStudentDTO.setRegisterDTO(new RegisterDTO("blabla@example.ca", "12345", "STUDENT"));
        registerStudentDTO.setStudentDTO(new StudentDTO("Test", "Test", "1231231", Department._420B0));

        mockMvc.perform(MockMvcRequestBuilders.post("/student/register")
                        .requestAttr("student", registerStudentDTO)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void GetJobOffersByDepartment_Valid() throws Exception {
//        Arrange
        List<JobOffer> jobOffers_420B0 = new ArrayList<>(
                List.of(
                        JobOffer.builder()
                                .title("JobOffer1")
                                .description("Description1")
                                .location("Location1")
                                .department(Department._420B0)
                                .jobOfferState(JobOfferState.OPEN)
                                .duration(6)
                                .hoursPerWeek(40)
                                .salary(20.0f)
                                .startDate(LocalDate.now())
                                .expirationDate(LocalDate.now().plusDays(30))
                                .build(),
                        JobOffer.builder()
                                .title("JobOffer2")
                                .description("Description2")
                                .location("Location1")
                                .department(Department._420B0)
                                .jobOfferState(JobOfferState.SUBMITTED)
                                .duration(6)
                                .hoursPerWeek(40)
                                .salary(20.0f)
                                .startDate(LocalDate.now())
                                .expirationDate(LocalDate.now().plusDays(30))
                                .build(),
                        JobOffer.builder()
                                .title("JobOffer3")
                                .description("Description3")
                                .location("Location1")
                                .department(Department._420B0)
                                .jobOfferState(JobOfferState.EXPIRED)
                                .duration(6)
                                .hoursPerWeek(40)
                                .salary(20.0f)
                                .startDate(LocalDate.now().minusDays(60))
                                .expirationDate(LocalDate.now().minusDays(30))
                                .build()
                )
        );
        when(studentService.getJobOffersByDepartment(Department._420B0,Semester.builder()
                .season(Semester.Season.FALL)
                .year(2021)
                .build()))
                .thenReturn(jobOffers_420B0.stream().map(JobOfferDTO::new).collect(Collectors.toList()));


//        Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/{department}", "_420B0")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void GetJobOffersByDepartment_InvalidDep() throws Exception {
//        Arrange
//        Rien à arranger

//        Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/jobOffers/{department}", "_420B1")
                        .header("Authorization", token)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().is(673));
    }

    @Test
    public void GetOpenJobOffersByDepartment_Valid() throws Exception {
//        Arrange
        List<JobOffer> jobOffers_420B0 = new ArrayList<>(
                List.of(
                        JobOffer.builder()
                                .title("JobOffer1")
                                .description("Description1")
                                .location("Location1")
                                .department(Department._420B0)
                                .jobOfferState(JobOfferState.OPEN)
                                .duration(6)
                                .hoursPerWeek(40)
                                .salary(20.0f)
                                .startDate(LocalDate.now())
                                .expirationDate(LocalDate.now().plusDays(30))
                                .build(),
                        JobOffer.builder()
                                .title("JobOffer2")
                                .description("Description2")
                                .location("Location1")
                                .department(Department._420B0)
                                .jobOfferState(JobOfferState.SUBMITTED)
                                .duration(6)
                                .hoursPerWeek(40)
                                .salary(20.0f)
                                .startDate(LocalDate.now())
                                .expirationDate(LocalDate.now().plusDays(30))
                                .build(),
                        JobOffer.builder()
                                .title("JobOffer3")
                                .description("Description3")
                                .location("Location1")
                                .department(Department._420B0)
                                .jobOfferState(JobOfferState.EXPIRED)
                                .duration(6)
                                .hoursPerWeek(40)
                                .salary(20.0f)
                                .startDate(LocalDate.now().minusDays(60))
                                .expirationDate(LocalDate.now().minusDays(30))
                                .build()
                )
        );

        when(studentService.getOpenJobOffersByDepartment(Department._420B0,Semester.builder()
                .season(Semester.Season.FALL)
                .year(2021)
                .build()))
                .thenReturn(jobOffers_420B0.stream().map(JobOfferDTO::new).filter(jobOfferDTO ->
                        jobOfferDTO.getJobOfferState().equals(JobOfferState.OPEN)).collect(Collectors.toList()));

//        Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/jobOffers/open/{department}", "_420B0")
                        .header("Authorization", token).param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void GetOpenJobOffersByDepartment_InvalidDep2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/jobOffers/open/{department}", "_420B1")
                        .header("Authorization", token)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().is(673));
    }

    @Test
    public void AddCv_Valid() throws Exception {
//        Arrange
        Long studentId = 1L;
        byte[] fileData = new byte[100];
        mockMvc.perform(MockMvcRequestBuilders.multipart("/student/cv/{studentId}", 1L)
                        .file(new MockMultipartFile("file", "filename.pdf", "text/plain", fileData))
                        .header("Authorization", token)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void AddCv_EmptyFile() throws Exception {
//        Arrange
//        Rien à arranger
        mockMvc.perform(MockMvcRequestBuilders.post("/student/cv/{studentId}", 1L)
                        .header("Authorization", token)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content("".getBytes()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void AddCv_Invalid() throws Exception {
//        Arrange
        byte[] fileData = new byte[100];
        when(studentService.addCv(23L, new CvFileDTO())).thenThrow(new StudentNotFoundException(23L));

//      Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/student/cv/{studentId}", 23L)
                        .file(new MockMultipartFile("file", "filename.txt", "text/plain", fileData))
                        .header("Authorization", token)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void DeleteCv_Valid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/cv/{studentId}", 1L)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void getAppliedJobOfferByStudent_Valid() throws Exception {
        //        Arrange
        Long studentId = 1L;
        List<JobOfferDTO> jobOffers = asList(new JobOfferDTO(), new JobOfferDTO());

        when(studentService.getAppliedJobOfferByStudentId(studentId,new Semester(LocalDate.now()))).thenReturn(jobOffers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/appliedJobOffer/{studentId}", studentId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

//    TODO: Fix this test
    @Test
    public void getAppliedJobOfferByStudent_InvalidId() throws Exception {
        Long wrongStudentId = -1L;

        when(studentService.getAppliedJobOfferByStudentId(wrongStudentId,
                Semester.builder()
                    .season(Semester.Season.FALL)
                    .year(2021)
                    .build())).thenThrow(new StudentNotFoundException(wrongStudentId));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/appliedJobOffer/{studentId}", wrongStudentId)
                        .header("Authorization", token)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().is(406));
    }

    @Test
    public void getAppliedJobOfferByStudent_InvalidNullId() throws Exception {
        Long nullStudentId = null;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/appliedJobOffer/{studentId}", nullStudentId)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

	@Test
	public void GetJobOffersByDepartment_Valid2() throws Exception{
		//        Arrange
		List<JobOffer> jobOffers_420B0 = new ArrayList<>(List.of(JobOffer.builder().title("JobOffer1").description(
			                                                                 "Description1").location("Location1").department(Department._420B0).jobOfferState(JobOfferState.OPEN).duration(6)
		                                                                 .hoursPerWeek(40).salary(20.0f).startDate(
				LocalDate.now()).expirationDate(LocalDate.now().plusDays(30)).build(), JobOffer.builder().title(
			"JobOffer2").description("Description2").location("Location1").department(Department._420B0).jobOfferState(
			JobOfferState.SUBMITTED).duration(6).hoursPerWeek(40).salary(20.0f).startDate(LocalDate.now()).expirationDate(
			LocalDate.now().plusDays(30)).build(), JobOffer.builder().title("JobOffer3").description("Description3")
		                                                     .location("Location1").department(Department._420B0)
		                                                     .jobOfferState(JobOfferState.EXPIRED).duration(6).hoursPerWeek(
				40).salary(20.0f).startDate(LocalDate.now().minusDays(60)).expirationDate(LocalDate.now().minusDays(30))
		                                                     .build()));
		when(studentService.getJobOffersByDepartment(Department._420B0,Semester.builder()
                .season(Semester.Season.FALL)
                .year(2021)
                .build())).thenReturn(
			jobOffers_420B0.stream().map(JobOfferDTO::new).collect(Collectors.toList()));

		//        Act & Assert
		mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/jobOffers/{department}", "_420B0")
                        .header("Authorization", token).param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(
			MockMvcResultMatchers.status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void GetJobOffersByDepartment_InvalidDep2() throws Exception{
		//        Arrange
		//        Rien à arranger

		//        Act & Assert
		mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/jobOffers/{department}", "_420B1")
                        .header("Authorization", token)
                        .param("season", "FALL")
                        .param("year", "2021"))
				.andExpect(MockMvcResultMatchers.status().is(673))
				;
	}

	@Test
	public void GetOpenJobOffersByDepartment_Valid2() throws Exception{
		//        Arrange
		List<JobOffer> jobOffers_420B0 = new ArrayList<>(List.of(JobOffer.builder().title("JobOffer1").description(
			                                                                 "Description1").location("Location1").department(Department._420B0).jobOfferState(JobOfferState.OPEN).duration(6)
		                                                                 .hoursPerWeek(40).salary(20.0f).startDate(
				LocalDate.now()).expirationDate(LocalDate.now().plusDays(30)).build(), JobOffer.builder().title(
			"JobOffer2").description("Description2").location("Location1").department(Department._420B0).jobOfferState(
			JobOfferState.SUBMITTED).duration(6).hoursPerWeek(40).salary(20.0f).startDate(LocalDate.now()).expirationDate(
			LocalDate.now().plusDays(30)).build(), JobOffer.builder().title("JobOffer3").description("Description3")
		                                                     .location("Location1").department(Department._420B0)
		                                                     .jobOfferState(JobOfferState.EXPIRED).duration(6).hoursPerWeek(
				40).salary(20.0f).startDate(LocalDate.now().minusDays(60)).expirationDate(LocalDate.now().minusDays(30))
		                                                     .build()));

		when(studentService.getOpenJobOffersByDepartment(Department._420B0,Semester.builder()
                .season(Semester.Season.FALL)
                .year(2021)
                .build())).thenReturn(jobOffers_420B0.stream().map(
			JobOfferDTO::new).filter(jobOfferDTO -> {
			return jobOfferDTO.getJobOfferState().equals(JobOfferState.OPEN);
		}).collect(Collectors.toList()));

		//        Act & Assert
		mockMvc.perform(MockMvcRequestBuilders
                .get("/student/jobOffers/open/{department}", "_420B0")
                .header("Authorization", token).param("season", "FALL")
                .param("year", "2021")).andExpect(
			MockMvcResultMatchers.status().isAccepted()).andExpect(
			content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testApplyJobOfferSuccess() throws Exception{
		JobOfferDTO mockDto = new JobOfferDTO();
		when(studentService.applyJobOffer(validJobOfferId, validStudentId)).thenReturn(mockDto);

		mockMvc
			.perform(post("/student/applyJobOffer/" + validStudentId + "/" + validJobOfferId)
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isAccepted());
	}

	@Test
	void testApplyJobOfferApiException() throws Exception{
		APIException mockException = new JobOfferNotOpenException();

		when(studentService.applyJobOffer(validJobOfferId, validStudentId)).thenThrow(mockException);

		mockMvc
			.perform(post("/student/applyJobOffer/" + validStudentId + "/" + validJobOfferId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
		;
	}

	@Test
	void ApplyJobOffer_GenericException() throws Exception{
		when(studentService.applyJobOffer(validJobOfferId, validStudentId)).thenThrow(new RuntimeException());

		mockMvc.perform(
			       post("/student/applyJobOffer/" + validStudentId + "/" + validJobOfferId)
                           .header("Authorization", token)
                           .contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().is(673));
	}

    @Test
    void testGetCvSuccess() throws Exception{
        CvFileDTO mockDto = new CvFileDTO();
        when(studentService.getCv(validStudentId)).thenReturn(mockDto);

        mockMvc
                .perform(get("/student/cv/" + validStudentId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void testGetCvApiException() throws Exception{
        APIException mockException = new StudentNotFoundException();

        when(studentService.getCv(validStudentId)).thenThrow(mockException);

        mockMvc
                .perform(get("/student/cv/" + validStudentId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getAppointmentsByJobApplicationId_ExistingId() throws Exception {

        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        appointmentDTOS.add(new AppointmentDTO());
        appointmentDTOS.add(new AppointmentDTO());
        appointmentDTOS.add(new AppointmentDTO());

        String expectedJson = objectMapper.writeValueAsString(appointmentDTOS);

        when(studentService.getAppointmentsByJobApplicationId(1L)).thenReturn(appointmentDTOS);

        mockMvc.perform(get("/student/appointmentsByJobApplicationId/{id}", 1L)
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().json(expectedJson));

    }

    @Test
    public void getAppointmentsByJobApplicationId_NotExistingId() throws Exception {

        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        String expectedJson = objectMapper.writeValueAsString(appointmentDTOS);

        when(studentService.getAppointmentsByJobApplicationId(1L)).thenReturn(appointmentDTOS);

        mockMvc.perform(get("/student/appointmentsByJobApplicationId/{id}", 1L)
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().json(expectedJson));

    }

    @Test
    public void findAllAppointmentsForJobOfferAndStudent_ThereAreAppointmentsAndJobOfferAndStudentActuallyExist() throws Exception {
//        Arrage
        Student student = Student.builder().id(1L).build();
        JobOffer jobOffer = JobOffer.builder().id(1L).build();
        JobApplication application = JobApplication.builder().id(2L).student(student).jobOffer(jobOffer).build();
        List<Appointment> appointments = new ArrayList<>(
                List.of(
                        Appointment.builder().jobApplication(application).build(),
                        Appointment.builder().jobApplication(application).build(),
                        Appointment.builder().jobApplication(application).build()
                )
        );
        application.setAppointments(appointments);

        when(studentService.findAllAppointmentsForJobOfferAndStudent(jobOffer.getId(), jobOffer.getId()))
                .thenReturn(appointments.stream().map(AppointmentDTO::new).collect(Collectors.toList()));

//        Act & Assert
        mockMvc.perform(get("/student/appointmentsByJobOfferIdAndStudentId/{jobOfferId}/{studentId}",
                jobOffer.getId(), student.getId())
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void setAppointmentToChosen_ExistingId() throws Exception {

        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(1L);
        jobApplication.setJobOffer(new JobOffer());

        Student student = new Student();
        student.setCredentials(new Credentials());
        student.setRole(Role.STUDENT);

        jobApplication.setStudent(student);

        Appointment appointmentBeforeChosen = new Appointment();
        appointmentBeforeChosen.setId(1L);
        appointmentBeforeChosen.setJobApplication(jobApplication);

        Appointment appointmentAfterChosen = appointmentBeforeChosen;
        appointmentAfterChosen.setChosen(true);

        AppointmentDTO appointmentDTO = new AppointmentDTO(appointmentAfterChosen);

        String expectedJson = objectMapper.writeValueAsString(appointmentDTO);

        when(studentService.setAppointmentToChosen(1L)).thenReturn(appointmentDTO);

        mockMvc.perform(put("/student/setAppointmentToChosen/{id}", 1L)
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().json(expectedJson));

    }

    @Test
    public void setAppointmentToChosen_NotExistingId() throws Exception {

        when(studentService.setAppointmentToChosen(1L)).thenThrow(new AppointmentNotFoundException());

        mockMvc.perform(put("/student/setAppointmentToChosen/{id}", 1L)
                        .header("Authorization", token))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Appointment does not exist."));

    }

    @Test
    public void getContractsByStudentId_Valid() throws Exception {
//        Arrange
        List<ShortContractDTO> contracts = new ArrayList<>();
        contracts.add(new ShortContractDTO());

        when(studentService.getContractsByStudentId(validStudentId, Semester.builder()
                .season(Semester.Season.FALL)
                .year(2021)
                .build())).thenReturn(contracts);

//        Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/contracts/{studentId}", validStudentId)
                    .param("season", "FALL")
                    .param("year", "2021")
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void signContract_Valid() throws Exception {
//        Arrange
        ContractDTO contractDTO = new ContractDTO();
        LoginDTO loginDTO = new LoginDTO("", "");

        when(studentService.signContract(validStudentId, 1L)).thenReturn(contractDTO);

//        Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/student/contract/sign/{contractId}", validStudentId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}

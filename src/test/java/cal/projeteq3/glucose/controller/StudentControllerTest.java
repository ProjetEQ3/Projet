package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.config.SecurityConfiguration;
import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.exception.badRequestException.StudentNotFoundException;
import cal.projeteq3.glucose.exception.unauthorizedException.JobOfferNotOpenException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.UserRepository;
import cal.projeteq3.glucose.security.JwtAuthenticationEntryPoint;
import cal.projeteq3.glucose.security.JwtTokenProvider;
import cal.projeteq3.glucose.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	private final Long validStudentId = 1L;

	private final Long validJobOfferId = 1L;

    private final String token =
"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb3Vpc0BtaWNoYXVkLmNvbSIsImlhdCI6MTY5NzU4NjUwNCwiZXhwIjoxNjk3NjcyOTA0LCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiU1RVREVOVCJ9XX0.rWbsnxpbiOMbzuxHGGVREdrTdULU0oAXluAg7Sq5YhQ";

    @BeforeEach
    public void setup() {
        when(userRepository.findUserByCredentialsEmail(anyString())).thenReturn(Optional.of(Student.builder().build()));
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
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
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
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("JobOffer1"));
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
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("JobOffer1"));
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
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
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
        List<JobOfferDTO> jobOffers = Arrays.asList(new JobOfferDTO(), new JobOfferDTO());

        when(studentService.getAppliedJobOfferByStudentId(studentId,new Semester(LocalDate.now()))).thenReturn(jobOffers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/appliedJobOffer/{studentId}", studentId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(jobOffers.size()));
    }

    @Test
    public void getAppliedJobOfferByStudent_InvalidWrongId() throws Exception {
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
			MockMvcResultMatchers.status().isAccepted()).andExpect(
			MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
			MockMvcResultMatchers.jsonPath("$.length()").value(3)).andExpect(
			MockMvcResultMatchers.jsonPath("$[0].title").value("JobOffer1"));
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
			MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
			MockMvcResultMatchers.jsonPath("$.length()").value(1)).andExpect(
			MockMvcResultMatchers.jsonPath("$[0].title").value("JobOffer1"));
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
}

package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.request.StudentNotFoundException;
import cal.projeteq3.glucose.exception.unauthorizedException.InvalidEmailOrPasswordException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {StudentController.class, CustomExceptionHandler.class})
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
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
        when(studentService.getJobOffersByDepartment(Department._420B0))
                .thenReturn(jobOffers_420B0.stream().map(JobOfferDTO::new).collect(Collectors.toList()));


//        Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/{department}", "_420B0"))
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
        mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/{department}", "_420B1"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
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

        when(studentService.getOpenJobOffersByDepartment(Department._420B0))
                .thenReturn(jobOffers_420B0.stream().map(JobOfferDTO::new).filter(jobOfferDTO ->
                        jobOfferDTO.getJobOfferState().equals(JobOfferState.OPEN)).collect(Collectors.toList()));

//        Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/open/{department}", "_420B0"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("JobOffer1"));
    }

    @Test
    public void GetOpenJobOffersByDepartment_InvalidDep() throws Exception {
//        Arrange
//        Rien à arranger

//        Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/open/{department}", "_420B1"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    public void AddCv_Valid() throws Exception {
//        Arrange
        Long studentId = 1L;
        byte[] fileData = new byte[100];
        mockMvc.perform(MockMvcRequestBuilders.multipart("/student/cv/{studentId}", 1L)
                        .file(new MockMultipartFile("file", "filename.pdf", "text/plain", fileData))
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
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content("".getBytes())
        )
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
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void DeleteCv_Valid() throws Exception {
//        Arrange
//        Rien à arranger
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/cv/{studentId}", 1L)
        )
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

//    @Test
//    public void DeleteCv_Invalid() throws Exception {
////        Arrange
//        when(studentService.deleteCv(239486723L)).thenThrow(new StudentNotFoundException(239486723L));
////        Rien à arranger
//        mockMvc.perform(MockMvcRequestBuilders.delete("/student/cv/{studentId}", 239486723L)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .content("".getBytes())
//        )
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }
}

package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.request.StudentNotFoundException;
import cal.projeteq3.glucose.exception.unauthorisedException.InvalidEmailOrPasswordException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.StudentService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {StudentController.class, StudentService.class})
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @Test
    public void Register_Valid() throws Exception {
//        Arrange
        RegisterStudentDTO registerStudentDTO = new RegisterStudentDTO();
        registerStudentDTO.setRegisterDTO(new RegisterDTO("blabla@example.ca", "Ose12345", "STUDENT"));
        registerStudentDTO.setStudentDTO(new StudentDTO());

        when(studentService.createStudent(registerStudentDTO)).thenReturn(new StudentDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/student/register")
                        .requestAttr("student", registerStudentDTO)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void Register_InvalidPassword() throws Exception {
//        Arrange
        RegisterStudentDTO registerStudentDTO = new RegisterStudentDTO();
        registerStudentDTO.setRegisterDTO(new RegisterDTO("blabla@example.ca", "123456", "STUDENT"));
        registerStudentDTO.setStudentDTO(new StudentDTO());

        when(studentService.createStudent(registerStudentDTO)).thenThrow(new InvalidEmailOrPasswordException());

        mockMvc.perform(MockMvcRequestBuilders.post("/student/register")
                        .requestAttr("student", registerStudentDTO)
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
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
                                .startDate(LocalDateTime.now())
                                .expirationDate(LocalDateTime.now().plusDays(30))
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
                                .startDate(LocalDateTime.now())
                                .expirationDate(LocalDateTime.now().plusDays(30))
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
                                .startDate(LocalDateTime.now().minusDays(60))
                                .expirationDate(LocalDateTime.now().minusDays(30))
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
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.header().exists("X-Errors"))
                .andExpect(MockMvcResultMatchers.header().string("X-Errors", "Invalide operation"));
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
                                .startDate(LocalDateTime.now())
                                .expirationDate(LocalDateTime.now().plusDays(30))
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
                                .startDate(LocalDateTime.now())
                                .expirationDate(LocalDateTime.now().plusDays(30))
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
                                .startDate(LocalDateTime.now().minusDays(60))
                                .expirationDate(LocalDateTime.now().minusDays(30))
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
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.header().exists("X-Errors"))
                .andExpect(MockMvcResultMatchers.header().string("X-Errors", "Invalide operation"));
    }

    @Test
    public void AddCv_Valid() throws Exception {
//        Arrange
        byte[] fileData = new byte[100];
        mockMvc.perform(MockMvcRequestBuilders.multipart("/student/cv/{studentId}", 1L)
                        .file(new MockMultipartFile("file", "filename.txt", "text/plain", fileData))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted());
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
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
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

package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import cal.projeteq3.glucose.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {ManagerController.class, ManagerService.class, CustomExceptionHandler.class})
@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ManagerService managerService;
    @MockBean
    private StudentService studentService;
    @MockBean
    private EmployerService employerService;

    @Test
    public void getAllJobOffer_valid() throws Exception {
        // Arrange
        List<JobOfferDTO> jobOffers = Arrays.asList(new JobOfferDTO(), new JobOfferDTO());

        when(managerService.getAllJobOffer()).thenReturn(jobOffers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffers/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(jobOffers.size()));
    }

    @Test
    public void getJobOfferById_valid() throws Exception {
        // Arrange
        Long jobId = 1L;
        JobOfferDTO jobOffer = new JobOfferDTO();
        jobOffer.setId(jobId);
        jobOffer.setTitle("Sample Job");

        when(managerService.getJobOfferByID(jobId)).thenReturn(jobOffer);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffer/{id}", jobId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(jobOffer.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(jobOffer.getTitle()));
    }

    @Test
    public void getJobOfferByEmployer_valid() throws Exception {
//        Arrange
        Long employerId = 1L;
        List<JobOfferDTO> jobOffers = Arrays.asList(new JobOfferDTO(), new JobOfferDTO());

        when(employerService.getJobOffersDTOByEmployerId(employerId)).thenReturn(jobOffers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffers/employer/{employerId}", employerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(jobOffers.size()));
    }

    @Test
    public void getJobOfferByState_valid() throws Exception {
        // Arrange
        String jobOfferState = "OPEN";
        List<JobOfferDTO> jobOffers = Arrays.asList(new JobOfferDTO(), new JobOfferDTO());

        when(managerService.getJobOffersWithState(JobOfferState.OPEN)).thenReturn(jobOffers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffers/{jobOfferState}", jobOfferState)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(jobOffers.size()));
    }

    @Test
    public void updateJobOfferStateOPEN_valid() throws Exception {
        // Arrange
        Long jobId = 1L;
        JobOfferDTO jobOffer = new JobOfferDTO();
        jobOffer.setId(jobId);
        jobOffer.setTitle("Sample Job");

        when(managerService.updateJobOfferState(jobId, JobOfferState.OPEN, null)).thenReturn(jobOffer);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/manager/jobOffer/accept/{id}", jobId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(jobOffer.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(jobOffer.getTitle()));
    }

    @Test
    public void updateJobOfferStateREFUSED_valid() throws Exception {
        // Arrange
        Long jobId = 1L;
        JobOfferDTO jobOfferDto = new JobOfferDTO();
        jobOfferDto.setId(jobId);
        jobOfferDto.setTitle("Sample Job");

        String requestBody = "{\"reason\": \"Some reason\"}";

        when(managerService.updateJobOfferState(jobId, JobOfferState.REFUSED, "Some reason")).thenReturn(jobOfferDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/manager/jobOffer/refuse/{id}", jobId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(jobOfferDto.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(jobOfferDto.getTitle()))
        ;
    }

    @Test
    public void testUpdateCvState() throws Exception {
        // Arrange
        Long cvId = 1L;
        CvFileDTO cvFile = new CvFileDTO();
        cvFile.setId(cvId);
        cvFile.setFileName("SampleCv.pdf");

        when(managerService.updateCvState(cvId, CvState.ACCEPTED, "Approved")).thenReturn(cvFile);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/manager/cv/update/{id}", cvId)
                        .param("newCvState", "ACCEPTED")
                        .param("reason", "Approved")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cvFile.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileName").value(cvFile.getFileName()));
    }

    @Test
    public void testGetAllCV() throws Exception {
        // Arrange
        List<CvFileDTO> cvList = new ArrayList<>();
        CvFileDTO cv1 = new CvFileDTO();
        cv1.setId(1L);
        cv1.setFileName("Cv1.pdf");
        CvFileDTO cv2 = new CvFileDTO();
        cv2.setId(2L);
        cv2.setFileName("Cv2.pdf");
        cvList.add(cv1);
        cvList.add(cv2);

        when(managerService.getAllCv()).thenReturn(cvList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/cvs/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(cv1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fileName").value(cv1.getFileName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(cv2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fileName").value(cv2.getFileName()));

    }

    @Test
    public void testGetCvsSubmitted() throws Exception {
        // Arrange
        List<CvFileDTO> submittedCvList = new ArrayList<>();
        CvFileDTO cv1 = new CvFileDTO();
        cv1.setId(1L);
        cv1.setFileName("SubmittedCv1.pdf");
        CvFileDTO cv2 = new CvFileDTO();
        cv2.setId(2L);
        cv2.setFileName("SubmittedCv2.pdf");
        submittedCvList.add(cv1);
        submittedCvList.add(cv2);

        when(managerService.getSubmittedCv()).thenReturn(submittedCvList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/cvs/pending")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(cv1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fileName").value(cv1.getFileName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(cv2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fileName").value(cv2.getFileName()));
    }

}


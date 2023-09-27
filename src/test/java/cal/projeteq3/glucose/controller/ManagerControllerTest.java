package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import cal.projeteq3.glucose.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {ManagerController.class, ManagerService.class})
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
    public void testGetAllJobOffer() throws Exception {
        when(managerService.getAllJobOffer()).thenReturn(List.of(new JobOfferDTO()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffers/all")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetJobOfferById() throws Exception {
        when(managerService.getJobOfferByID(1L)).thenReturn(new JobOfferDTO());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffer/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetJobOfferByEmploye() throws Exception {
        when(employerService.getJobOffersDTOByEmployerId(1L)).thenReturn(List.of(new JobOfferDTO()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffers/employer/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetJobOfferByState() throws Exception {
        when(managerService.getJobOffersWithState(JobOfferState.OPEN)).thenReturn(List.of(new JobOfferDTO()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffers/OPEN")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateJobOfferState() throws Exception {
        when(managerService.updateJobOfferState(1L, JobOfferState.OPEN, null)).thenReturn(new JobOfferDTO());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/manager/jobOffer/accept/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateJobOfferState2() throws Exception {
        when(managerService.updateJobOfferState(1L, JobOfferState.REFUSED, "reason")).thenReturn(new JobOfferDTO());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/manager/jobOffer/refuse/1").requestAttr("reason", "reason")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateCvState() throws Exception {
        when(managerService.updateCvState(1L, CvState.ACCEPTED, "reason")).thenReturn(new CvFileDTO());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/manager/cv/update/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateCvState2() throws Exception {
        when(managerService.updateCvState(1L, CvState.REFUSED, "reason")).thenReturn(new CvFileDTO());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/manager/cv/update/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllCV() throws Exception {
        when(managerService.getAllCv()).thenReturn(List.of(new CvFileDTO()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/manager/cvs/all")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetCvsPending() throws Exception {
        when(managerService.getSubmittedCv()).thenReturn(List.of(new CvFileDTO()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/manager/cvs/pending")).andExpect(MockMvcResultMatchers.status().isOk());
    }

//     @Test
//     public void testGetCvById() throws Exception {
//         CvFileDTO cvFileDTO = new CvFileDTO();
//         cvFileDTO.setCvState(CvState.ACCEPTED);
//         cvFileDTO.setFileData(new byte[1]);
//         when(managerService.getCvById(1L)).thenReturn(cvFileDTO);
//         this.mockMvc.perform(MockMvcRequestBuilders.get("/manager/cv/1")).andExpect(MockMvcResultMatchers.status().isOk());
//     }
}


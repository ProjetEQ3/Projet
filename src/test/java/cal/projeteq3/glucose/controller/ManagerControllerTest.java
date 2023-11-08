package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.config.SecurityConfiguration;
import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.repository.ContractRepository;
import cal.projeteq3.glucose.repository.UserRepository;
import cal.projeteq3.glucose.security.JwtAuthenticationEntryPoint;
import cal.projeteq3.glucose.security.JwtTokenProvider;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {ManagerController.class, ManagerService.class, CustomExceptionHandler.class,
        SecurityConfiguration.class, JwtTokenProvider.class, JwtAuthenticationEntryPoint.class})
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
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String token;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        when(userRepository.findUserByCredentialsEmail(anyString())).thenReturn(Optional.of(Manager.builder().build()));
        token = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("michel@michaud.com","Ose12345"));
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllJobOffer_valid() throws Exception {
        // Arrange
        List<JobOfferDTO> jobOffers = Arrays.asList(new JobOfferDTO(), new JobOfferDTO());

        when(managerService.getAllJobOffer(new Semester(LocalDate.now()))).thenReturn(jobOffers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/manager/jobOffers/all")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(jobOffers.size()))
        ;
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
                        .header("Authorization", token)
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

        when(employerService.getJobOffersDTOByEmployerId(employerId,new Semester(LocalDate.now()))).thenReturn(jobOffers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffers/employer/{employerId}", employerId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(jobOffers.size()))
                ;
    }

    @Test
    public void getJobOfferByState_valid() throws Exception {
        // Arrange
        String jobOfferState = "OPEN";
        List<JobOfferDTO> jobOffers = Arrays.asList(new JobOfferDTO(), new JobOfferDTO());

        when(managerService.getJobOffersWithState(JobOfferState.OPEN,new Semester(LocalDate.now()))).thenReturn(jobOffers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/jobOffers/{jobOfferState}", jobOfferState)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("season", "FALL")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(jobOffers.size()))
        ;
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
                        .header("Authorization", token)
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
                        .header("Authorization", token)
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
                        .header("Authorization", token)
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
                        .header("Authorization", token)
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
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(cv1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fileName").value(cv1.getFileName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(cv2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fileName").value(cv2.getFileName()));
    }

    @Test
    public void getAllContracts_valid() throws Exception{
        // Arrange
        List<ContractDTO> contracts = Arrays.asList(new ContractDTO(), new ContractDTO());

        when(managerService.getContractsBySession(new Semester(LocalDate.now()))).thenReturn(contracts);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/contracts")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("season", "FALL")
                            .param("year", "2021"))
                    .andExpect(MockMvcResultMatchers.status().isAccepted())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getContractById_valid() throws Exception{
        Long contractId = 1L;

        when(userService.getShortContractById(contractId)).thenReturn(new ContractDTO());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/contract/" + contractId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void signContract_valid() throws Exception{
        Long contractId = 1L;
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("Test@test.com");
        loginDTO.setPassword("Test12345");

        when(managerService.signContract(contractId, 1L)).thenReturn(new ContractDTO());
        when(userService.authenticateUserContractSigning(loginDTO)).thenReturn(1L);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/manager/contract/sign/{contractId}", contractId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}


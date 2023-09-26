package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.request.EmployerNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.EmployerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cal.projeteq3.glucose.repository.JobOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

    @MockBean
    private JobOfferRepository jobOfferRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createEmployerTest() {

        //Arrange
        RegisterEmployerDTO registerEmployerDTO =
                new RegisterEmployerDTO(
                new RegisterDTO("michel", "michaud", "EMPLOYER"),
                new EmployerDTO("Fritz", "123-456-7890", null)
        );

        Employer employer = Employer.builder()
                .firstName("michel")
                .lastName("michaud")
                .email("test@test.com")
                .password("Ose12asd3")
                .organisationName("Fritz")
                .organisationPhone("123-456-7890")
                .build();
        when(employerRepository.save(employer)).thenReturn(
                Employer.builder()
                        .firstName("michel")
                        .lastName("michaud")
                        .email("test@test.com")
                        .password("Ose12asd3")
                        .organisationName("Fritz")
                        .organisationPhone("123-456-7890")
                        .build()
        );

        //Act

//        employerService.createEmployer(employer);

        //Assert

        verify(employerRepository, times(1)).save(employer);

    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer() {


        employerService.updateEmployer(1L, new EmployerDTO());
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    void testUpdateEmployer2() {
        assertThrows(EmployerNotFoundException.class, () -> employerService.updateEmployer(7L, new EmployerDTO()));
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer3() {

        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(1L);
        employerService.updateEmployer(1L, updatedEmployer);
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer4() {

        employerService.updateEmployer(1L, null);
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer5() {

        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(1L);
        employerService.updateEmployer(2L, updatedEmployer);
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer6() {

        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(7L);
        employerService.updateEmployer(1L, updatedEmployer);
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer7() {

        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setEmail("jane.doe@example.org");
        updatedEmployer.setId(1L);
        employerService.updateEmployer(1L, updatedEmployer);
    }

    @Test
    public void getAllEmployersTest() {

        //Arrange

        List<Employer> employers = new ArrayList<>();

        Employer employer1 =
                Employer.builder()
                .firstName("michel1")
                .lastName("michaud1")
                .email("test1@test.com")
                .password("Ose12asd3")
                .organisationName("Fritz")
                .organisationPhone("123-456-7890")
                .build();
        Employer employer2 = Employer.builder()
                .firstName("michel2")
                .lastName("michaud2")
                .email("test2@test.com")
                .password("Ose12asd3")
                .organisationName("Fritz")
                .organisationPhone("123-456-7890")
                .build();

        employers.add(employer1);
        employers.add(employer2);

        when(employerRepository.findAll()).thenReturn(employers);

        //Act

        List<EmployerDTO> employerList = employerService.getAllEmployers();

        //Assert

        /*assertEquals(2, employerList.size());*/
        verify(employerRepository, times(1)).findAll();

    }

    @Test
    public void getEmployerByIDTest() {

        //Arrange

        Long id = 1L;
        Employer employer = Employer.builder()
                .firstName("michel")
                .lastName("michaud")
                .email("test@test.com")
                .password("Ose12asd3")
                .organisationName("Fritz")
                .organisationPhone("123-456-7890")
                .build();
        when(employerRepository.findById(id)).thenReturn(Optional.of(employer));

        //Act

        Optional<EmployerDTO> employerDTO = employerService.getEmployerByID(id);

        //Assert

        /*assertTrue(employerDTO.isPresent());*/
        verify(employerRepository, times(1)).findById(id);

    }

    @Test
    public void updateEmployerTest() {

        // Arrange

        Long employerId = 1L;
        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(employerId);
        updatedEmployer.setFirstName("UpdatedFirstName");
        updatedEmployer.setLastName("UpdatedLastName");
        updatedEmployer.setEmail("updated@example.com");
        updatedEmployer.setOrganisationName("UpdatedOrgName");
        updatedEmployer.setOrganisationPhone("123-456-7890");

        Employer existingEmployer = new Employer();
        existingEmployer.setId(employerId);
        existingEmployer.setFirstName("OriginalFirstName");
        existingEmployer.setLastName("OriginalLastName");
        /*existingEmployer.setEmail("original@example.com");*/
        existingEmployer.setOrganisationName("OriginalOrgName");
        existingEmployer.setOrganisationPhone("987-654-3210");

        when(employerRepository.findById(employerId)).thenReturn(Optional.of(existingEmployer));
        when(employerRepository.save(any(Employer.class))).thenReturn(existingEmployer);

        // Act

        EmployerDTO updatedDTO = employerService.updateEmployer(employerId, updatedEmployer);

        // Assert

        assertNotNull(updatedDTO);
        assertEquals(updatedEmployer.getId(), updatedDTO.getId());
        assertEquals(updatedEmployer.getFirstName(), updatedDTO.getFirstName());
        assertEquals(updatedEmployer.getLastName(), updatedDTO.getLastName());
        assertEquals(updatedEmployer.getEmail(), updatedDTO.getEmail());
        assertEquals(updatedEmployer.getOrganisationName(), updatedDTO.getOrganisationName());
        assertEquals(updatedEmployer.getOrganisationPhone(), updatedDTO.getOrganisationPhone());

    }

    /**
     * Method under test: {@link EmployerService#createJobOffer(JobOfferDTO, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateJobOffer() {

        employerService.createJobOffer(new JobOfferDTO(), 1L);
    }

    /**
     * Method under test: {@link EmployerService#createJobOffer(JobOfferDTO, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateJobOffer2() {

        JobOfferDTO jobOffer = new JobOfferDTO();
        jobOffer.setId(1L);
        employerService.createJobOffer(jobOffer, 1L);
    }

    /**
     * Method under test: {@link EmployerService#createJobOffer(JobOfferDTO, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateJobOffer3() {
        employerService.createJobOffer(null, 1L);
    }

    /**
     * Method under test: {@link EmployerService#createJobOffer(JobOfferDTO, Long)}
     */
    @Test
    void testCreateJobOffer4() {
        assertThrows(EmployerNotFoundException.class, () -> employerService.createJobOffer(new JobOfferDTO(), 7L));
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateJobOffer() {

        employerService.updateJobOffer(new JobOfferDTO());
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateJobOffer2() {

        JobOfferDTO updatedJobOffer = new JobOfferDTO();
        updatedJobOffer.setId(1L);
        employerService.updateJobOffer(updatedJobOffer);
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    void testUpdateJobOffer3() {
        // Arrange
        JobOfferDTO nullJobOfferDTO = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            employerService.updateJobOffer(nullJobOfferDTO);
        });
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    void testUpdateJobOffer4() {
        /*LocalDateTime startDate = LocalDate.of(1970, 1, 1).atStartOfDay();
        JobOfferDTO actualUpdateJobOfferResult = employerService.updateJobOffer(
                new JobOfferDTO(1L, "Dr", Department._420B0, "Location", "The characteristics of someone or something", 10.0f,
                        startDate, 1, LocalDate.of(1970, 1, 1).atStartOfDay(), JobOfferState.SUBMITTED, 1, "Just cause"));
        assertEquals(Department._420B0, actualUpdateJobOfferResult.getDepartment());
        assertEquals("Dr", actualUpdateJobOfferResult.getTitle());
        assertEquals(10.0f, actualUpdateJobOfferResult.getSalary());
        assertEquals("00:00", actualUpdateJobOfferResult.getStartDate().toLocalTime().toString());
        assertEquals("Location", actualUpdateJobOfferResult.getLocation());
        assertEquals(JobOfferState.SUBMITTED, actualUpdateJobOfferResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferResult.getId().longValue());
        assertEquals(1, actualUpdateJobOfferResult.getHoursPerWeek());
        assertEquals(1, actualUpdateJobOfferResult.getDuration());
        assertEquals("00:00", actualUpdateJobOfferResult.getExpirationDate().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualUpdateJobOfferResult.getDescription());*/
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    void testUpdateJobOffer5() {
        JobOfferDTO updatedJobOffer = new JobOfferDTO();
        updatedJobOffer.setId(9L);
        /*assertThrows(JobOffreNotFoundException.class, () -> employerService.updateJobOffer(updatedJobOffer));*/
    }

    @Test
    public void deleteEmployerTest() {

        // Arrange

        Long employerId = 1L;
        doNothing().when(employerRepository).deleteById(employerId);

        // Act

        employerService.deleteEmployer(employerId);

        // Assert

        verify(employerRepository, times(1)).deleteById(employerId);

    }

    /**
     * Method under test: {@link EmployerService#getJobOffersDTOByEmployerId(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetJobOffersDTOByEmployerId(){
        // Arrange
        Long employerId = 1L;
        List<JobOfferDTO> jobOffers = new ArrayList<>();
        jobOffers.add(new JobOfferDTO());
        when(employerRepository.findById(employerId)).thenReturn(Optional.of(new Employer()));
        when(employerService.getJobOffersDTOByEmployerId(employerId)).thenReturn(jobOffers);

        // Act
        List<JobOfferDTO> jobOfferDTOs = employerService.getJobOffersDTOByEmployerId(employerId);

        // Assert
        assertNotNull(jobOfferDTOs);
        assertFalse(jobOfferDTOs.isEmpty());
    }

    /**
     * Method under test: {@link EmployerService#getJobOffersDTOByEmployerId(Long)}
     */
    @Test
    void testGetJobOffersDTOByEmployerId2() {
        assertTrue(employerService.getJobOffersDTOByEmployerId(7L).isEmpty());
    }

    @Test
    public void testCreateEmployer() {
        RegisterEmployerDTO registerEmployerDTO = new RegisterEmployerDTO();
        when(employerRepository.save(any(Employer.class))).thenReturn(new Employer());
        /*EmployerDTO employerDTO = employerService.createEmployer(registerEmployerDTO);
        assertNotNull(employerDTO);*/
    }

    @Test
    public void testGetAllEmployers() {
        List<Employer> employers = new ArrayList<>();
        when(employerRepository.findAll()).thenReturn(employers);
        List<EmployerDTO> employerDTOs = employerService.getAllEmployers();
        assertNotNull(employerDTOs);
        assertTrue(employerDTOs.isEmpty());
    }

    @Test
    public void testGetEmployerByEmail() {
        String email = "test@example.com";
        Optional<Employer> employerOptional = Optional.of(new Employer());
        when(employerRepository.findByCredentialsEmail(email)).thenReturn(employerOptional);
        //EmployerDTO employerDTO = employerService.getEmployerByEmail(email);
        //assertNotNull(employerDTO);
    }

    @Test
    public void testGetEmployerByEmailNotFound() {
        String email = "test@example.com";
        when(employerRepository.findByCredentialsEmail(email)).thenReturn(Optional.empty());
        assertThrows(EmployerNotFoundException.class, () -> employerService.getEmployerByEmail(email));
    }

    @Test
    public void testGetEmployerByID() {
        Long id = 1L;
        Optional<Employer> employerOptional = Optional.of(new Employer());
        when(employerRepository.findById(id)).thenReturn(employerOptional);
        Optional<EmployerDTO> employerDTOOptional = employerService.getEmployerByID(id);
        //assertTrue(employerDTOOptional.isPresent());
    }

    @Test
    public void testGetEmployerByIDNotFound() {
        Long id = 1L;
        when(employerRepository.findById(id)).thenReturn(Optional.empty());
        Optional<EmployerDTO> employerDTOOptional = employerService.getEmployerByID(id);
        assertFalse(employerDTOOptional.isPresent());
    }

    @Test
    public void testGetStudentsByJobOfferId() {
        Long testJobOfferId = 1L;

        // Arrange the mock objects
        JobOffer mockJobOffer = new JobOffer();
        JobApplication mockJobApplication = new JobApplication();
        Student mockStudent = new Student();
        mockStudent.setFirstName("John");
        mockStudent.setLastName("Doe");
        mockJobApplication.setStudent(mockStudent);
        mockJobOffer.setJobApplications(List.of(mockJobApplication));
        when(jobOfferRepository.findById(anyLong())).thenReturn(Optional.of(mockJobOffer));

        // Act
        List<StudentDTO> result = employerService.getStudentsByJobOfferId(testJobOfferId);

        // Assert the results
        assertNotNull(result);
        assertEquals(1, result.size());

        StudentDTO returnedStudent = result.get(0);
        assertEquals("John", returnedStudent.getFirstName());

        verify(jobOfferRepository, times(1)).findById(testJobOfferId);
    }

}

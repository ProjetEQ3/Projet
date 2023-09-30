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
import java.time.LocalDate;

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
<<<<<<< HEAD
import java.util.Collections;
=======
import java.util.Arrays;
>>>>>>> origin/EQ3-13
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

<<<<<<< HEAD
=======
    @MockBean
    private JobOfferRepository jobOfferRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
>>>>>>> origin/EQ3-13

    @Test
    void createEmployer_valid(){
//        Arrange
        RegisterDTO registerDTO = new RegisterDTO(
                "Test@Test.com",
                "Testestest1",
                "EMPLOYER");
        EmployerDTO employerDTO = new EmployerDTO(
                "Test",
                "Test",
                null);

        RegisterEmployerDTO registerEmployerDTO = new RegisterEmployerDTO(
                registerDTO,
                employerDTO);

        Employer employer = Employer.builder()
                .email(registerEmployerDTO.getRegisterDTO().getEmail())
                .password(registerEmployerDTO.getRegisterDTO().getPassword())
                .firstName(registerEmployerDTO.getEmployerDTO().getFirstName())
                .lastName(registerEmployerDTO.getEmployerDTO().getLastName())
                .organisationName(registerEmployerDTO.getEmployerDTO().getOrganisationName())
                .organisationPhone(registerEmployerDTO.getEmployerDTO().getOrganisationPhone())
                .build();

        when(employerRepository.save(employer)).thenReturn(employer);

//        Act
        EmployerDTO employerDTOResult = employerService.createEmployer(registerEmployerDTO);

//        Assert
        assertEquals(employerDTOResult.getFirstName(), employer.getFirstName());
        assertEquals(employerDTOResult.getLastName(), employer.getLastName());
        assertEquals(employerDTOResult.getOrganisationName(), employer.getOrganisationName());
        assertEquals(employerDTOResult.getOrganisationPhone(), employer.getOrganisationPhone());
        assertEquals(employerDTOResult.getEmail(), employer.getEmail());
        verify(employerRepository, times(1)).save(employer);
    }

    @Test
    void getAllEmployers_valid(){
//        Arrange
        List<Employer> employers = new ArrayList<>(
                List.of(
                        Employer.builder()
                                .id(1L)
                                .email("test@test.com")
                                .password("Testestest1")
                                .firstName("Test")
                                .lastName("Test")
                                .organisationName("Test")
                                .organisationPhone("111-111-1111")
                                .build(),
                        Employer.builder()
                                .id(2L)
                                .email("test2@test.com")
                                .password("Testestest1")
                                .firstName("Test")
                                .lastName("Test")
                                .organisationName("Test")
                                .organisationPhone("111-111-1111")
                                .build(),
                        Employer.builder()
                                .id(3L)
                                .email("test3@test.com")
                                .password("Testestest1")
                                .firstName("Test")
                                .lastName("Test")
                                .organisationName("Test")
                                .organisationPhone("111-111-1111")
                                .build()
                )
        );

        when(employerRepository.findAll()).thenReturn(employers);

//        Act
        List<EmployerDTO> employerDTOS = employerService.getAllEmployers();

<<<<<<< HEAD
//        Assert
        assertEquals(employerDTOS.size(), employers.size());
=======
        List<EmployerDTO> employerList = employerService.getAllEmployers();

        //Assert

        /*assertEquals(2, employerList.size());*/
>>>>>>> origin/EQ3-13
        verify(employerRepository, times(1)).findAll();
    }

    @Test
    void getEmployerByEmail_valid(){
//        Arrange
        Employer employer = Employer.builder()
                .id(1L)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();

        when(employerRepository.findByCredentialsEmail(employer.getEmail())).thenReturn(Optional.of(employer));

//        Act
        EmployerDTO employerDTOResult = employerService.getEmployerByEmail(employer.getEmail());

//        Assert
        assertEquals(employerDTOResult.getFirstName(), employer.getFirstName());
        assertEquals(employerDTOResult.getLastName(), employer.getLastName());
        assertEquals(employerDTOResult.getOrganisationName(), employer.getOrganisationName());
        assertEquals(employerDTOResult.getOrganisationPhone(), employer.getOrganisationPhone());
        assertEquals(employerDTOResult.getEmail(), employer.getEmail());
        verify(employerRepository, times(1)).findByCredentialsEmail(employer.getEmail());
    }

    @Test
    void getEmployerByEmail_invalid(){
//        Arrange
        Employer employer = Employer.builder()
                .id(1L)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();

        when(employerRepository.findByCredentialsEmail(employer.getEmail())).thenReturn(Optional.empty());

//        Act & Assert
        EmployerNotFoundException employerNotFoundException =
                assertThrows(EmployerNotFoundException.class, () ->
                        employerService.getEmployerByEmail(employer.getEmail()));

        verify(employerRepository, times(1)).findByCredentialsEmail(employer.getEmail());
    }

    @Test
    void getEmployerByID_valid(){
//        Arrange
        Employer employer = Employer.builder()
                .id(1L)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();

        when(employerRepository.findById(employer.getId())).thenReturn(Optional.of(employer));

//        Act
        EmployerDTO employerDTOResult = employerService.getEmployerByID(employer.getId());

//        Assert
        assertEquals(employerDTOResult.getFirstName(), employer.getFirstName());
        assertEquals(employerDTOResult.getLastName(), employer.getLastName());
        assertEquals(employerDTOResult.getOrganisationName(), employer.getOrganisationName());
        assertEquals(employerDTOResult.getOrganisationPhone(), employer.getOrganisationPhone());
        assertEquals(employerDTOResult.getEmail(), employer.getEmail());
        verify(employerRepository, times(1)).findById(employer.getId());
    }

    @Test
    void getEmployerByID_invalid(){
//        Arrange
        Employer employer = Employer.builder()
                .id(1L)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();

        when(employerRepository.findById(employer.getId())).thenReturn(Optional.empty());

//        Act & Assert
        assertThrows(EmployerNotFoundException.class, () ->
                employerService.getEmployerByID(employer.getId()));

        verify(employerRepository, times(1)).findById(employer.getId());
    }

    @Test
    void updateEmployer_valid(){
//        Arrange
        Long id = 1L;
        Employer employer = Employer.builder()
                .id(id)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();

        EmployerDTO employerDTO = new EmployerDTO(
                "Test42",
                "222-222-2222",
                null);

        when(employerRepository.findById(id)).thenReturn(Optional.of(employer));
        when(employerRepository.save(employer)).thenReturn(employer);

//        Act
        EmployerDTO employerDTOResult = employerService.updateEmployer(id, employerDTO);

//        Assert
        assertEquals(employerDTOResult.getFirstName(), employer.getFirstName());
        assertEquals(employerDTOResult.getLastName(), employer.getLastName());
        assertEquals(employerDTOResult.getOrganisationName(), employer.getOrganisationName());
        assertEquals(employerDTOResult.getOrganisationPhone(), employer.getOrganisationPhone());
        assertEquals(employerDTOResult.getEmail(), employer.getEmail());
        verify(employerRepository, times(1)).findById(id);
        verify(employerRepository, times(1)).save(employer);
    }

    @Test
    void updateEmployer_invalid(){
//        Arrange
        Long id = 1L;
        Employer employer = Employer.builder()
                .id(id)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();

        EmployerDTO employerDTO = new EmployerDTO(
                "Test42",
                "222-222-2222",
                null);

        when(employerRepository.findById(id)).thenReturn(Optional.empty());

//        Act & Assert
        assertThrows(EmployerNotFoundException.class, () ->
                employerService.updateEmployer(id, employerDTO));

        verify(employerRepository, times(1)).findById(id);
    }

    @Test
    void deleteEmployer_valid(){
//        Arrange
        Long id = 1L;

//        Act
        employerService.deleteEmployer(id);

//        Assert
        verify(employerRepository, times(1)).deleteById(id);
    }

    @Test
    void getJobOffersDTOByEmployerId_valid(){
//        Arrange
        Long id = 1L;
        List<JobOffer> jobOffers = new ArrayList<>(
                List.of(
                        JobOffer.builder()
                                .id(1L)
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
                                .id(2L)
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
                                .id(3L)
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

        Employer employer = Employer.builder()
                .id(id)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .jobOffers(jobOffers)
                .build();

        when(employerRepository.findById(id)).thenReturn(Optional.of(employer));

//        Act
        List<JobOfferDTO> jobOfferDTOS = employerService.getJobOffersDTOByEmployerId(id);

<<<<<<< HEAD
//        Assert
        assertEquals(jobOfferDTOS.size(), jobOffers.size());
=======
        Optional<EmployerDTO> employerDTO = employerService.getEmployerByID(id);

        //Assert

        /*assertTrue(employerDTO.isPresent());*/
>>>>>>> origin/EQ3-13
        verify(employerRepository, times(1)).findById(id);
    }

    @Test
<<<<<<< HEAD
    void getJobOffersDTOByEmployerId_invalidId(){
//        Arrange
        Long id = 1L;
=======
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
>>>>>>> origin/EQ3-13

        when(employerRepository.findById(id)).thenReturn(Optional.empty());

//        Act & Assert
        assertThrows(EmployerNotFoundException.class, () ->
                employerService.getJobOffersDTOByEmployerId(id));

        verify(employerRepository, times(1)).findById(id);
    }

    @Test
<<<<<<< HEAD
    void getJobOffersDTOByEmployerId_NoJobOffers(){
//        Arrange
        Long id = 1L;
        List<JobOffer> jobOffers = new ArrayList<>();

        Employer employer = Employer.builder()
                .id(id)
                .email("Employer employer")
                .password("Employer employer")
                .firstName("Employer employer")
                .lastName("Employer employer")
                .organisationName("Employer employer")
                .organisationPhone("Employer employer")
                .jobOffers(jobOffers)
                .build();

        when(employerRepository.findById(id)).thenReturn(Optional.of(employer));

//        Act
        List<JobOfferDTO> jobOfferDTOS = employerService.getJobOffersDTOByEmployerId(id);

//        Assert
        assertEquals(jobOfferDTOS.size(), jobOffers.size());
        verify(employerRepository, times(1)).findById(id);
    }

    @Test
    void getJobOffersDTOByEmployerId_NullJobOffers(){
//        Arrange
        Long id = 1L;

        Employer employer = Employer.builder()
                .id(id)
                .email("Employer employer")
                .password("Employer employer")
                .firstName("Employer employer")
                .lastName("Employer employer")
                .organisationName("Employer employer")
                .organisationPhone("Employer employer")
                .build();

        when(employerRepository.findById(id)).thenReturn(Optional.of(employer));

//        Act
        List<JobOfferDTO> jobOfferDTOS = employerService.getJobOffersDTOByEmployerId(id);

//        Assert
        assertEquals(jobOfferDTOS.size(), 0);
        verify(employerRepository, times(1)).findById(id);
    }

    @Test
    public void CreateJobOffer_valid() {
        // Arrange
        Long employerId = 1L;
        Employer employer = Employer.builder()
                .id(employerId)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();


        JobOfferDTO jobOfferDTO = new JobOfferDTO(
                1L,
                "JobOffer1",
                Department._420B0,
                "Location1",
                "Description1",
                20.0f,
                LocalDate.now(),
                6,
                LocalDate.now().plusDays(30),
                JobOfferState.OPEN,
                40,
                null
        );

        when(employerRepository.findById(employerId)).thenReturn(Optional.of(employer));
        when(jobOfferRepository.save(any())).thenReturn(jobOfferDTO.toEntity());

        // Act
        JobOfferDTO result = employerService.createJobOffer(jobOfferDTO, employerId);

        // Assert
        assertNotNull(result);
        assertEquals(jobOfferDTO.getTitle(), result.getTitle());
        assertEquals(jobOfferDTO.getDepartment(), result.getDepartment());
        assertEquals(jobOfferDTO.getLocation(), result.getLocation());
        assertEquals(jobOfferDTO.getDescription(), result.getDescription());
        assertEquals(jobOfferDTO.getSalary(), result.getSalary());
        assertEquals(jobOfferDTO.getStartDate(), result.getStartDate());
        assertEquals(jobOfferDTO.getDuration(), result.getDuration());
        assertEquals(jobOfferDTO.getExpirationDate(), result.getExpirationDate());
        assertEquals(jobOfferDTO.getJobOfferState(), result.getJobOfferState());
        assertEquals(jobOfferDTO.getHoursPerWeek(), result.getHoursPerWeek());
        assertEquals(jobOfferDTO.getRefusReason(), result.getRefusReason());
        verify(employerRepository, times(1)).findById(employerId);
        verify(jobOfferRepository, times(1)).save(any());
    }

    @Test
    public void UpdateJobOffer_valid() {
        // Arrange
        Long jobOfferId = 1L;
        JobOfferDTO updatedJobOffer = new JobOfferDTO();
        updatedJobOffer.setId(jobOfferId);

        JobOffer returnedJobOffer = JobOffer.builder()
                .id(jobOfferId)
                .build();

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.of(returnedJobOffer));
        when(jobOfferRepository.save(any())).thenReturn(returnedJobOffer);

        // Act
        JobOfferDTO result = employerService.updateJobOffer(updatedJobOffer);

        System.out.println(result);

        // Assert
        assertNotNull(result);
        assertEquals(jobOfferId, result.getId());
        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        verify(jobOfferRepository, times(1)).save(any());
    }

    @Test
    public void DeleteJobOffer_valid() {
        // Arrange
        Long jobOfferId = 1L;

        // Act
        employerService.deleteJobOffer(jobOfferId);

        // Assert
        verify(jobOfferRepository, times(1)).deleteById(jobOfferId);
    }

    @Test
    public void GetAllJobOffers_Empty() {
        // Arrange
        Long employerId = 1L;
        when(jobOfferRepository.findJobOfferByEmployer_Id(employerId)).thenReturn(Collections.emptyList());

        // Act
        List<JobOfferDTO> result = employerService.getAllJobOffers(employerId);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(jobOfferRepository, times(1)).findJobOfferByEmployer_Id(employerId);
    }

    @Test
    public void GetAllJobOffers_List() {
        // Arrange
        Long employerId = 1L;
        List<JobOffer> jobOffers = new ArrayList<>(List.of(
                JobOffer.builder()
                        .title("JobOffer1")
                        .department(Department._420B0)
                        .location("Location1")
                        .description("Description1")
                        .salary(20.0f)
                        .startDate(LocalDate.now())
                        .duration(6)
                        .expirationDate(LocalDate.now().plusDays(30))
                        .jobOfferState(JobOfferState.OPEN)
                        .hoursPerWeek(40)
                        .build(),
                JobOffer.builder()
                        .title("JobOffer2")
                        .department(Department._420B0)
                        .location("Location1")
                        .description("Description1")
                        .salary(20.0f)
                        .startDate(LocalDate.now())
                        .duration(6)
                        .expirationDate(LocalDate.now().plusDays(30))
                        .jobOfferState(JobOfferState.OPEN)
                        .hoursPerWeek(40)
                        .build(),
                JobOffer.builder()
                        .title("JobOffer3")
                        .department(Department._420B0)
                        .location("Location1")
                        .description("Description1")
                        .salary(20.0f)
                        .startDate(LocalDate.now())
                        .duration(6)
                        .expirationDate(LocalDate.now().plusDays(30))
                        .jobOfferState(JobOfferState.OPEN)
                        .hoursPerWeek(40)
                        .build()
        ));
        when(jobOfferRepository.findJobOfferByEmployer_Id(employerId)).thenReturn(jobOffers);

        // Act
        List<JobOfferDTO> result = employerService.getAllJobOffers(employerId);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(jobOfferRepository, times(1)).findJobOfferByEmployer_Id(employerId);
=======
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
>>>>>>> origin/EQ3-13
    }

}

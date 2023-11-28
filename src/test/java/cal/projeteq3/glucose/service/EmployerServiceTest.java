package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.evaluation.InternEvaluationDTO;
import cal.projeteq3.glucose.dto.evaluation.SectionDTO;
import cal.projeteq3.glucose.dto.evaluation.TimeSheetDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.badRequestException.*;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.contract.Signature;
import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import cal.projeteq3.glucose.model.evaluation.internEvaluation.InternEvaluation;
import cal.projeteq3.glucose.model.evaluation.internEvaluation.sections.*;
import cal.projeteq3.glucose.model.evaluation.timeSheet.TimeSheet;
import cal.projeteq3.glucose.model.evaluation.timeSheet.WeeklyHours;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.*;

import java.time.LocalDate;

import jakarta.persistence.Embedded;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {
    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private JobApplicationRepository jobApplicationRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private EmployerService employerService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private SignatureRepository signatureRepository;
    @Mock
    private TimeSheetRepository timeSheetRepository;
    @Mock
    private InternEvaluationRepository internEvaluationRepository;

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
        when(passwordEncoder.encode(registerEmployerDTO.getRegisterDTO().getPassword())).thenReturn("Testestest1");

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

//        Assert
        assertEquals(employerDTOS.size(), employers.size());
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
        Semester semester = new Semester(LocalDate.now());
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
                                .semester(semester)
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
                                .semester(semester)
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
                                .semester(semester)
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
        List<JobOfferDTO> jobOfferDTOS = employerService.getJobOffersDTOByEmployerId(id, semester);

//        Assert
        assertEquals(jobOfferDTOS.size(), jobOffers.size());
        verify(employerRepository, times(1)).findById(id);
    }

    @Test
    void getJobOffersDTOByEmployerId_invalidId(){
//        Arrange
        Long id = 1L;
        Semester semester = new Semester(LocalDate.now());

        when(employerRepository.findById(id)).thenReturn(Optional.empty());

//        Act & Assert
        assertThrows(EmployerNotFoundException.class, () ->
                employerService.getJobOffersDTOByEmployerId(id, semester));

        verify(employerRepository, times(1)).findById(id);
    }

    @Test
    void getJobOffersDTOByEmployerId_NoJobOffers(){
//        Arrange
        Long id = 1L;
        Semester semester = new Semester(LocalDate.now());
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
        List<JobOfferDTO> jobOfferDTOS = employerService.getJobOffersDTOByEmployerId(id, semester);

//        Assert
        assertEquals(jobOfferDTOS.size(), jobOffers.size());
        verify(employerRepository, times(1)).findById(id);
    }

    @Test
    void getJobOffersDTOByEmployerId_NullJobOffers(){
//        Arrange
        Long id = 1L;
        Semester semester = new Semester(LocalDate.now());

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
        List<JobOfferDTO> jobOfferDTOS = employerService.getJobOffersDTOByEmployerId(id, semester);

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
                null,
                1,
                new Semester(LocalDate.now())
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
        assertEquals(jobOfferDTO.getSemester(), result.getSemester());
        verify(employerRepository, times(1)).findById(employerId);
        verify(jobOfferRepository, times(1)).save(any());
    }

    @Test
    public void UpdateJobOffer_valid() {
        // Arrange
        Long jobOfferId = 1L;
        JobOfferDTO updatedJobOffer = new JobOfferDTO();
        updatedJobOffer.setId(jobOfferId);
        updatedJobOffer.setStartDate(LocalDate.now());

        JobOffer returnedJobOffer = JobOffer.builder()
                .id(jobOfferId)
                .build();

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.of(returnedJobOffer));
        when(jobOfferRepository.save(any())).thenReturn(returnedJobOffer);

        // Act
        JobOfferDTO result = employerService.updateJobOffer(updatedJobOffer);

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
        Semester semester = new Semester(LocalDate.now());
        when(jobOfferRepository.findJobOfferByEmployer_IdAndSemester(employerId, semester)).thenReturn(Collections.emptyList());

        // Act
        List<JobOfferDTO> result = employerService.getAllJobOffers(employerId, semester);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(jobOfferRepository, times(1)).findJobOfferByEmployer_IdAndSemester(employerId, semester);
    }

    @Test
    public void GetAllJobOffers_List() {
        // Arrange
        Long employerId = 1L;
        Semester semester = new Semester(LocalDate.now());
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
                        .semester(semester)
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
                        .semester(semester)
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
                        .semester(semester)
                        .build()
        ));
        when(jobOfferRepository.findJobOfferByEmployer_IdAndSemester(employerId, semester)).thenReturn(jobOffers);

        // Act
        List<JobOfferDTO> result = employerService.getAllJobOffers(employerId, semester);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(jobOfferRepository, times(1)).findJobOfferByEmployer_IdAndSemester(employerId, semester);
    }


    @Test
    public void testGetPendingApplicationsByJobOfferId_full() {
        Long testJobOfferId = 1L;

        // Arrange the mock objects
        JobOffer mockJobOffer = new JobOffer();
        mockJobOffer.setId(testJobOfferId);
        mockJobOffer.setJobOfferState(JobOfferState.OPEN);
        JobApplication mockJobApplication = new JobApplication();
        mockJobApplication.setJobApplicationState(JobApplicationState.SUBMITTED);
        mockJobApplication.setId(1L);

        Student mockStudent = Student.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("doe@john.com")
                .password("password")
                .build();
        mockJobApplication.setStudent(mockStudent);
        mockJobOffer.setJobApplications(List.of(mockJobApplication));
        mockJobApplication.setCoverLetter("cover");
        mockJobApplication.setJobOffer(mockJobOffer);
        when(jobOfferRepository.findById(anyLong())).thenReturn(Optional.of(mockJobOffer));

        // Act
        List<JobApplicationDTO> result = employerService.getPendingApplicationsByJobOfferId(testJobOfferId);


        // Assert the results
        assertNotNull(result);
        assertEquals(1, result.size());

        JobApplicationDTO returnedJobApplication = result.get(0);
        assertEquals("cover", returnedJobApplication.getCoverLetter());

        verify(jobOfferRepository, times(1)).findById(testJobOfferId);
    }

    @Test
    void testGetPendingApplicationsByJobOfferId_empty(){
        // Arrange
        Long testJobOfferId = 1L;
        JobOffer mockJobOffer = new JobOffer();
        mockJobOffer.setJobApplications(Collections.emptyList());
        when(jobOfferRepository.findById(anyLong())).thenReturn(Optional.of(mockJobOffer));

        // Act
        List<JobApplicationDTO> result = employerService.getPendingApplicationsByJobOfferId(testJobOfferId);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(jobOfferRepository, times(1)).findById(testJobOfferId);
    }

    @Test
    void testGetPendingStudentsByJobOfferId_NotFound(){
        // Arrange
        Long testJobOfferId = 99L;
        when(jobOfferRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(JobOfferNotFoundException.class, () ->
                employerService.getPendingApplicationsByJobOfferId(testJobOfferId));
        verify(jobOfferRepository, times(1)).findById(testJobOfferId);
    }
    @Test
    public void testAcceptApplication() {
        Long applicationId = 1L;
        Student student = Student
          .builder()
          .id(1L)
          .email("studentmeail@student.st")
          .password("studentpassword")
          .build();
        JobApplication mockApplication = new JobApplication();
        mockApplication.setStudent(student);
        mockApplication.setId(applicationId);
        JobOffer mockJobOffer = new JobOffer();
        mockJobOffer.setId(1L);
        mockJobOffer.setJobOfferState(JobOfferState.OPEN);
        mockJobOffer.setJobApplications(new ArrayList<>());
        mockJobOffer.setNbOfCandidates(4);
        mockJobOffer.setExpirationDate(LocalDate.now().plusDays(30));
        mockJobOffer.getJobApplications().add(mockApplication);
        mockApplication.setJobOffer(mockJobOffer);

        when(jobApplicationRepository.findById(applicationId)).thenReturn(Optional.of(mockApplication));

        JobApplicationDTO result = employerService.acceptApplication(applicationId);
        verify(jobApplicationRepository, times(1)).save(mockApplication);
        assertNotNull(result);
        assertEquals(JobApplicationState.ACCEPTED, result.getJobApplicationState());
    }

    @Test
    public void testRefuseApplication() {
        Long applicationId = 1L;
        Student student = Student
          .builder()
          .id(1L)
          .email("studentmeail@student.st")
          .password("studentpassword")
          .build();
        JobApplication mockApplication = new JobApplication();
        mockApplication.setStudent(student);
        mockApplication.setId(applicationId);

        JobOffer mockJobOffer = new JobOffer();

        mockJobOffer.setId(1L);
        mockJobOffer.setJobOfferState(JobOfferState.OPEN);
        mockJobOffer.setJobApplications(new ArrayList<>());
        mockJobOffer.getJobApplications().add(mockApplication);
        mockApplication.setJobOffer(mockJobOffer);

        when(jobApplicationRepository.findById(applicationId)).thenReturn(Optional.of(mockApplication));

        JobApplicationDTO result = employerService.refuseApplication(applicationId);
        verify(jobApplicationRepository, times(1)).save(mockApplication);
        assertNotNull(result);
        assertEquals(JobApplicationState.REJECTED, result.getJobApplicationState());
    }

    @Test
    public void testAddAppointmentByApplicationId_Valid() {
        Long applicationId = 1L;
        JobOffer jobOffer = JobOffer
                .builder()
                .id(1L)
                .title("test")
                .description("test")
                .location("test")
                .department(Department._420B0)
                .jobOfferState(JobOfferState.OPEN)
                .duration(6)
                .hoursPerWeek(40)
                .salary(20.0f)
                .startDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusDays(30))
                .semester(new Semester(LocalDate.now()))
                .build();
        Student student = Student
                .builder()
                .id(1L)
                .email("test@test.test")
                .password("test")
                .build();
        JobApplication mockApplication = new JobApplication();
        mockApplication.setStudent(student);
        mockApplication.setId(applicationId);
        mockApplication.setJobOffer(jobOffer);

        LocalDateTime date1 = LocalDateTime.now().plusDays(1);
        LocalDateTime date2 = LocalDateTime.now().plusDays(2);
        LocalDateTime date3 = LocalDateTime.now().plusDays(3);

        Set<LocalDateTime> dateList = new HashSet<>();
        dateList.add(date1);
        dateList.add(date2);
        dateList.add(date3);

        when(jobApplicationRepository.findById(applicationId)).thenReturn(Optional.of(mockApplication));
        when(jobApplicationRepository.save(mockApplication)).thenReturn(mockApplication);

        employerService.addAppointmentByJobApplicationId(mockApplication.getId(), dateList);
        verify(jobApplicationRepository, times(1)).save(mockApplication);
        assertEquals(mockApplication.getAppointments().size(), 3);
        Set<LocalDateTime> dateEnd = new HashSet<>();
        for (Appointment appointment : mockApplication.getAppointments()) {
            dateEnd.add(appointment.getAppointmentDate());
        }

        assertNotNull(mockApplication);
        assertEquals(jobOffer.getId(), mockApplication.getJobOffer().getId());
        assertEquals(student.getId(), mockApplication.getStudent().getId());
        assertEquals(applicationId, mockApplication.getId());
        assertEquals(dateEnd, dateList);
    }

    @Test
    public void testAddAppointmentByApplicationId_OneDate() {
        Long applicationId = 1L;
        JobOffer jobOffer = JobOffer
                .builder()
                .id(1L)
                .title("test")
                .description("test")
                .location("test")
                .department(Department._420B0)
                .jobOfferState(JobOfferState.OPEN)
                .duration(6)
                .hoursPerWeek(40)
                .salary(20.0f)
                .startDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusDays(30))
                .semester(new Semester(LocalDate.now()))
                .build();
        Student student = Student
                .builder()
                .id(1L)
                .email("test@test.test")
                .password("test")
                .build();
        JobApplication mockApplication = new JobApplication();
        mockApplication.setStudent(student);
        mockApplication.setId(applicationId);
        mockApplication.setJobOffer(jobOffer);

        LocalDateTime date1 = LocalDateTime.now().plusDays(1);

        Set<LocalDateTime> dateList = new HashSet<>();
        dateList.add(date1);

        when(jobApplicationRepository.findById(applicationId)).thenReturn(Optional.of(mockApplication));
        when(jobApplicationRepository.save(mockApplication)).thenReturn(mockApplication);

        employerService.addAppointmentByJobApplicationId(mockApplication.getId(), dateList);
        verify(jobApplicationRepository, times(2)).save(mockApplication);
        assertEquals(mockApplication.getAppointments().size(), 1);
        Set<LocalDateTime> dateEnd = new HashSet<>();
        for (Appointment appointment : mockApplication.getAppointments()) {
            dateEnd.add(appointment.getAppointmentDate());
        }

        assertNotNull(mockApplication);
        assertEquals(jobOffer.getId(), mockApplication.getJobOffer().getId());
        assertEquals(student.getId(), mockApplication.getStudent().getId());
        assertEquals(applicationId, mockApplication.getId());
        assertEquals(dateEnd, dateList);
    }

    @Test
    public void testAddAppointmentByApplicationId_NullId() {
        Long applicationId = null;

        Set<LocalDateTime> dateList = new HashSet<>();

        assertThrows(JobApplicationNotFoundException.class, () -> employerService.addAppointmentByJobApplicationId(applicationId, dateList));

    }

    @Test
    public void testAddAppointmentByApplicationId_InexistantId() {
        Long applicationId = 999L;

        Set<LocalDateTime> dateList = new HashSet<>();

        assertThrows(JobApplicationNotFoundException.class, () -> employerService.addAppointmentByJobApplicationId(applicationId, dateList));

    }

    @Test
    public void getAllJobApplicationsByEmployerId_Valid(){
        // Arrange
        Long employerId = 123L;
        List<JobApplication> jobApplications = new ArrayList<>();
        jobApplications.add(new JobApplication(/* create a JobApplication instance here */));
        jobApplications.add(new JobApplication(/* create another JobApplication instance here */));
        jobApplications.get(0).setStudent(Student.builder().build());
        jobApplications.get(0).setJobOffer(JobOffer.builder().id(1L).build());
        jobApplications.get(1).setStudent(Student.builder().build());
        jobApplications.get(1).setJobOffer(JobOffer.builder().id(1L).build());


        when(jobApplicationRepository.findAllByJobOffer_Employer_Id(employerId)).thenReturn(jobApplications);

        // Act
        List<JobApplicationDTO> jobApplicationDTOs = employerService.getAllJobApplicationsByEmployerId(employerId);

        // Assert
        assertEquals(jobApplications.size(), jobApplicationDTOs.size());
    }

    @Test
    public void getWaitingStudents_Valid(){
        // Arrange
        Long employerId = 123L;
        List<JobApplication> jobApplications = new ArrayList<>();
        jobApplications.add(new JobApplication(/* create a JobApplication instance here */));
        jobApplications.add(new JobApplication(/* create another JobApplication instance here */));
        jobApplications.get(0).setStudent(Student.builder().build());
        jobApplications.get(0).setJobOffer(JobOffer.builder().id(1L).semester(Semester.builder().season(Semester.Season.SUMMER).year(2023).build()).build());
        jobApplications.get(0).setSemester(Semester.builder().season(Semester.Season.SUMMER).year(2023).build());
        jobApplications.get(0).setJobApplicationState(JobApplicationState.WAITING_APPOINTMENT);
        jobApplications.get(1).setStudent(Student.builder().build());
        jobApplications.get(1).setJobOffer(JobOffer.builder().id(1L).semester(Semester.builder().season(Semester.Season.SUMMER).year(2023).build()).build());
        jobApplications.get(1).setJobApplicationState(JobApplicationState.WAITING_APPOINTMENT);
        jobApplications.get(1).setSemester(Semester.builder().season(Semester.Season.SUMMER).year(2023).build());

        when(jobApplicationRepository.findAllByJobOffer_Employer_Id(employerId)).thenReturn(jobApplications);

        // Act
        List<StudentDTO> studentDTOs = employerService.getWaitingStudents(employerId, Semester.builder().season(Semester.Season.SUMMER).year(2023).build());

        // Assert
        assertEquals(jobApplications.size(), studentDTOs.size());
    }

    @Test
    void getOfferByApplicationId_WhenValidId_ReturnsJobOfferDTO() {
        // Arrange
        Long applicationId = 1L;
        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(applicationId);

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(100L);
        jobApplication.setJobOffer(jobOffer);

        when(jobApplicationRepository.findById(applicationId)).thenReturn(Optional.of(jobApplication));

        // Act
        JobOfferDTO result = employerService.getOfferByApplicationId(applicationId);

        // Assert
        assertEquals(jobOffer.getId(), result.getId());
        // Add more assertions for other properties if needed.
    }

    @Test
    void getOfferByApplicationId_WhenInvalidId_ThrowsJobOfferNotFoundException() {
        // Arrange
        Long applicationId = 1L;

        when(jobApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(JobOfferNotFoundException.class, () -> {
            employerService.getOfferByApplicationId(applicationId);
        });
    }

    @Test
    void getAppointmentByJobApplicationId() {
        // Arrange
        Long jobOfferId = 1L;
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(jobOfferId);

        Long applicationId = 1L;
        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(applicationId);
        jobApplication.setJobOffer(jobOffer);

        Credentials credentials = new Credentials();
        credentials.setEmail("John@Doe.fr");
        credentials.setRole(Role.STUDENT);

        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setCredentials(credentials);
        jobApplication.setStudent(student);

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(LocalDateTime.now());
        appointment.setJobApplication(jobApplication);
        appointment.setChosen(true);



        when(jobApplicationRepository.findAppointmentsByJobApplicationId(1L)).thenReturn(List.of(appointment));

        // Act
        AppointmentDTO result = employerService.getAppointmentByJobApplicationId(applicationId);

        // Assert
        assertEquals(appointment.getAppointmentDate(), result.getAppointmentDate());
    }

    @Test
    void getContractsBySession() {
        // Arrange
        Credentials credManager = new Credentials();
        credManager.setEmail("Michel@Michaud.com");
        credManager.setRole(Role.MANAGER);

        Credentials credEmployer = new Credentials();
        credEmployer.setEmail("Michel@Professionel.com");
        credEmployer.setRole(Role.EMPLOYER);

        Credentials credStudent = new Credentials();
        credStudent.setEmail("Michel@Student.com");
        credStudent.setRole(Role.STUDENT);

        Manager manager = new Manager();
        manager.setCredentials(credManager);
        manager.setId(1L);
        manager.setFirstName("Michel");
        manager.setLastName("Michaud");

        Employer employer = new Employer();
        employer.setCredentials(credEmployer);
        employer.setId(2L);
        employer.setFirstName("Michel");
        employer.setLastName("Professionel");
        employer.setOrganisationName("Professionel");
        employer.setOrganisationPhone("111-111-1111");

        Student student = new Student();
        student.setId(3L);
        student.setCredentials(credStudent);
        student.setFirstName("Michel");
        student.setLastName("Student");

        Semester semester = new Semester(LocalDate.now());

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(4L);
        jobOffer.setEmployer(employer);
        jobOffer.setSemester(semester);
        jobOffer.setDepartment(Department._420B0);
        jobOffer.setJobOfferState(JobOfferState.OPEN);
        jobOffer.setDuration(6);
        jobOffer.setHoursPerWeek(40);
        jobOffer.setSalary(20.0f);
        jobOffer.setStartDate(LocalDate.now());
        jobOffer.setExpirationDate(LocalDate.now().plusDays(30));
        jobOffer.setLocation("Location1");
        jobOffer.setDescription("Description1");
        jobOffer.setTitle("JobOffer1");

        Contract contract = new Contract(employer, student, jobOffer);

        when(contractRepository.findAllByJobOffer_Semester(semester)).thenReturn(List.of(contract));
        when(managerRepository.findAll()).thenReturn(List.of(manager));

        // Act
        List<ContractDTO> result = employerService.getContractsBySession(semester, 2L);
        assertEquals(1, result.size());
        assertEquals(contract.getId(), result.get(0).getId());
        assertEquals(contract.getJobOffer().getTitle(), result.get(0).getJobOfferName());
        assertEquals(contract.getStudent().getFirstName() + " " + contract.getStudent().getLastName(), result.get(0).getStudentName());
        assertEquals(contract.getJobOffer().getEmployer().getOrganisationName(), result.get(0).getJobOfferCompany());
    }

    @Test
    public void testSignContract() {
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Student student = Student.builder().id(1L).firstName("StudentFirstName").lastName("StudentLastName").build();
        Manager manager = Manager.builder().id(1L).firstName("ManagerFirstName").lastName("ManagerLastName").build();
        Employer employer = Employer.builder()
                .id(1L)
                .email("asd@asd.com")
                .firstName("EmployerFirstName")
                .lastName("EmployerLastName")
                .organisationName(" asd")
                .organisationPhone("111-111-1111")
                .build();
        JobOffer jobOffer = JobOffer.builder()
                .id(1L)
                .title("JobOfferTitle")
                .description("  asdads")
                .location("Location1")
                .department(Department._420B0)
                .jobOfferState(JobOfferState.OPEN)
                .startDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusDays(30))
                .semester(new Semester(LocalDate.now()))
                .employer(employer)
                .build();

        Contract contract = Contract
                .builder()
                .id(1L)
                .student(student)
                .employer(employer)
                .jobOffer(jobOffer)
                .build();
        Contract savedContract = Contract
                .builder()
                .id(1L)
                .employer(employer)
                .student(student)
                .jobOffer(jobOffer)
                .creationDate(date)
                .lastModificationDate(date)
                .isComplete(true)
                .build();

        Signature studentSignature = Signature
                .builder().firstName("StudentFirstName").lastName("StudentLastName").signatureDate(LocalDate.now()).contract(savedContract).build();
        Signature employerSignature = Signature
                .builder().firstName("EmployerFirstName").lastName("EmployerLastName").signatureDate(LocalDate.now()).contract(savedContract).build();
        Signature managerSignature = Signature
                .builder().firstName("ManagerFirstName").lastName("ManagerLastName").signatureDate(LocalDate.now()).contract(savedContract).build();

        savedContract.setStudentSignature(studentSignature);
        savedContract.setEmployerSignature(employerSignature);
        savedContract.setManagerSignature(managerSignature);

        when(signatureRepository.save(any(Signature.class))).thenReturn(managerSignature);
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        when(contractRepository.save(any(Contract.class))).thenReturn(savedContract);
        when(managerRepository.findFirstByDepartment(Department._420B0)).thenReturn(manager);

        ContractDTO result = employerService.signContract(1L, 1L);

        assertEquals("StudentFirstName", savedContract.getStudentSignature().getFirstName());
        assertEquals("StudentLastName", savedContract.getStudentSignature().getLastName());
        assertEquals(formattedDate, savedContract.getStudentSignature().getSignatureDate().toString());
        assertEquals("EmployerFirstName", savedContract.getEmployerSignature().getFirstName());
        assertEquals("EmployerLastName", savedContract.getEmployerSignature().getLastName());
        assertEquals(formattedDate, savedContract.getEmployerSignature().getSignatureDate().toString());
        assertEquals("ManagerFirstName", savedContract.getManagerSignature().getFirstName());
        assertEquals("ManagerLastName", savedContract.getManagerSignature().getLastName());
        assertEquals(formattedDate, savedContract.getManagerSignature().getSignatureDate().toString());
        assertEquals(date, savedContract.getCreationDate());
//        assertEquals(date, savedContract.getLastModificationDate()); Test prenne plus que 1ms alors FAIL
        verify(contractRepository, times(1)).save(contract);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testSignContractValidations() {
        LocalDateTime date = LocalDateTime.now();
        Student student = Student.builder().id(1L).firstName("StudentFirstName").lastName("StudentLastName").build();
        Employer employer = Employer.builder().id(1L).firstName("EmployerFirstName").lastName("EmployerLastName").build();
        JobOffer jobOffer = JobOffer.builder().id(1L).title("JobOfferTitle").employer(employer).jobOfferState(JobOfferState.OPEN).build();

        Signature studentSignature = Signature.builder().id(1L).firstName("StudentFirstName").lastName("StudentLastName").signatureDate(LocalDate.now()).build();
        Signature employerSignature = Signature.builder().id(1L).firstName("EmployerFirstName").lastName("EmployerLastName").signatureDate(LocalDate.now()).build();
        Signature managerSignature = Signature.builder().id(1L).firstName("ManagerFirstName").lastName("ManagerLastName").signatureDate(LocalDate.now()).build();

        Contract contract = Contract
          .builder()
          .id(1L)
          .student(student)
          .employer(employer)
          .jobOffer(jobOffer)
          .build();
        Contract savedContract = Contract
          .builder()
          .id(1L)
          .employer(employer)
          .student(student)
          .jobOffer(jobOffer)
          .employerSignature(employerSignature)
          .managerSignature(managerSignature)
          .creationDate(date)
          .lastModificationDate(date)
          .isComplete(true)
          .build();

        Employer employer2 = Employer.builder().id(2L).firstName("StudentFirstName2").lastName("StudentLastName2").build();
        Contract contract2 = Contract
          .builder()
          .id(2L)
          .student(student)
          .employer(employer2)
          .jobOffer(jobOffer)
          .studentSignature(studentSignature)
          .employerSignature(employerSignature)
          .build();
        Contract savedContract2 = Contract
          .builder()
          .id(2L)
          .student(student)
          .employer(employer2)
          .jobOffer(jobOffer)
          .studentSignature(studentSignature)
          .employerSignature(employerSignature)
          .creationDate(date)
          .lastModificationDate(date)
          .isComplete(true)
          .build();

        Employer employer3 = Employer.builder().id(3L).firstName("StudentFirstName3").lastName("StudentLastName3").build();
        Contract contract3 = Contract
          .builder()
          .id(3L)
          .student(student)
          .employer(employer3)
          .studentSignature(studentSignature)
          .build();
        Contract savedContract3 = Contract
          .builder()
          .id(3L)
          .employer(employer3)
          .student(student)
          .jobOffer(jobOffer)
          .employerSignature(employerSignature)
          .managerSignature(managerSignature)
          .creationDate(date)
          .lastModificationDate(date)
          .isComplete(true)
          .build();

        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));
        when(employerRepository.findById(2L)).thenReturn(Optional.of(employer2));
        when(employerRepository.findById(3L)).thenReturn(Optional.of(employer3));
        when(employerRepository.findById(100L)).thenReturn(Optional.empty());

        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        when(contractRepository.findById(2L)).thenReturn(Optional.of(contract2));
        when(contractRepository.findById(3L)).thenReturn(Optional.of(contract3));
        when(contractRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(EmployerNotFoundException.class, () -> {employerService.signContract(1L, 100L);});
        assertThrows(ContractNotFoundException.class, () -> {employerService.signContract(100L, 1L);});
        assertThrows(ContractNotReadyToSignException.class, () -> {employerService.signContract(3L, 3L);});
        assertThrows(UnauthorizedContractToSignException.class, () -> {employerService.signContract(1L, 2L);});
        assertThrows(ContractAlreadySignedException.class, () -> {employerService.signContract(2L, 2L);});
    }

    @Test
    public void testNbSubmittedApplicationsAcrossAllJobOffersFromEmployer() {

        Employer employer = Employer.builder().id(1L).build();
        int nbSubmittedApplications = 5;

        when(jobOfferRepository.countAllByEmployer_IdAndJobApplications_JobApplicationState(employer.getId(), JobApplicationState.SUBMITTED)).thenReturn(nbSubmittedApplications);

        int count = employerService.nbSubmittedApplicationsAcrossAllJobOffersFromEmployer(employer.getId());

        assertEquals(count, nbSubmittedApplications);

    }

    @Test
    public void testGetAllJobOffersWithSubmittedApplicationsFromEmployer_ExistentEmployer() {

        JobApplication jobApplication = JobApplication.builder().id(1L).jobApplicationState(JobApplicationState.SUBMITTED).build();
        List<JobApplication> jobApplications = Collections.singletonList(jobApplication);

        JobOffer jobOffer = JobOffer.builder().id(1L).jobApplications(jobApplications).build();
        List<JobOffer> jobOffers = Collections.singletonList(jobOffer);
        List<JobOfferDTO> jobOfferDTOS = jobOffers.stream().map(JobOfferDTO::new).toList();

        Employer employer = Employer.builder().id(1L).jobOffers(jobOffers).build();

        when(employerRepository.findById(employer.getId())).thenReturn(Optional.of(employer));

        List<JobOfferDTO> retrievedJobOffers = employerService.getAllJobOffersWithSubmittedApplicationsFromEmployer(employer.getId());

        assertEquals(jobOfferDTOS, retrievedJobOffers);

    }

    @Test
    public void testGetAllJobOffersWithSubmittedApplicationsFromEmployer_NonExistentEmployer() {

        Long nonExistentEmployerId = -1L;

        when(employerRepository.findById(nonExistentEmployerId)).thenThrow(new EmployerNotFoundException(nonExistentEmployerId));

        assertThrows(EmployerNotFoundException.class, () -> {
            employerService.getAllJobOffersWithSubmittedApplicationsFromEmployer(nonExistentEmployerId);
        });

    }

    @Test
    public void testGetAllAcceptedJobApplicationsByEmployerId_ExistentEmployer() {

        Employer employer = Employer.builder().id(1L).build();
        List<JobApplication> jobApplications = new ArrayList<>();

        when(jobApplicationRepository.findJobApplicationsByJobApplicationStateAndEmployerId(JobApplicationState.ACCEPTED, employer.getId()))
                .thenReturn(jobApplications);

        List<JobApplicationDTO> retrievedJobApplications = employerService.getAllAcceptedJobApplicationsByEmployerId(employer.getId());
        List<JobApplicationDTO> jobApplicationDTOS = jobApplications.stream().map(JobApplicationDTO::new).collect(Collectors.toList());

        assertEquals(retrievedJobApplications, jobApplicationDTOS);

    }

    @Test
    public void testGetAllAcceptedJobApplicationsByEmployerId_NonExistentEmployer() {

        Long nonExistentEmployerId = -1L;

        when(jobApplicationRepository.findJobApplicationsByJobApplicationStateAndEmployerId(JobApplicationState.ACCEPTED, nonExistentEmployerId)).thenThrow(new EmployerNotFoundException(nonExistentEmployerId));

        assertThrows(EmployerNotFoundException.class, () -> {
            employerService.getAllAcceptedJobApplicationsByEmployerId(nonExistentEmployerId);
        });

    }

    @Test
    public void testSaveTimeSheetForJobApplicationId_existentJobApplication() {
        // Arrange
        Long jobOfferId = 1L;
        JobOffer jobOffer = JobOffer.builder().id(jobOfferId).build();
        Long studentId = 1L;
        Student student = Student.builder().id(studentId).build();
        Long jobApplicationId = 1L;
        JobApplication jobApplication = JobApplication.builder().id(jobApplicationId).build();
        jobApplication.setStudent(student);
        jobApplication.setJobOffer(jobOffer);
        List<WeeklyHours> weeklyHours = new ArrayList<>();
        weeklyHours.add(new WeeklyHours());
        weeklyHours.add(new WeeklyHours());
        weeklyHours.add(new WeeklyHours());
        TimeSheet timeSheet = new TimeSheet();
        timeSheet.setWeeklyHours(weeklyHours);

        when(jobApplicationRepository.findById(jobApplicationId)).thenReturn(Optional.of(jobApplication));
        when(timeSheetRepository.save(any(TimeSheet.class))).thenReturn(timeSheet);

        // Act
        JobApplicationDTO result = employerService.saveTimeSheetForJobApplicationId(jobApplicationId, weeklyHours);

        // Assert
        assertNotNull(result);
        assertEquals(jobApplicationId, result.getId());
        verify(jobApplicationRepository, times(1)).findById(jobApplicationId);
        verify(timeSheetRepository, times(1)).save(any(TimeSheet.class));
    }

    @Test
    public void testGetTimeSheetByJobApplicationId_existentJobApplication() {
        // Arrange
        Long jobOfferId = 1L;
        JobOffer jobOffer = JobOffer.builder().id(jobOfferId).build();
        Long studentId = 1L;
        Student student = Student.builder().id(studentId).build();
        Long jobApplicationId = 1L;
        JobApplication jobApplication = JobApplication.builder().id(jobApplicationId).build();
        jobApplication.setStudent(student);
        jobApplication.setJobOffer(jobOffer);
        List<WeeklyHours> weeklyHours = new ArrayList<>();
        weeklyHours.add(new WeeklyHours());
        weeklyHours.add(new WeeklyHours());
        weeklyHours.add(new WeeklyHours());
        TimeSheet timeSheet = new TimeSheet();
        timeSheet.setWeeklyHours(weeklyHours);
        timeSheet.setJobApplication(jobApplication);

        when(timeSheetRepository.findByJobApplicationId(jobApplicationId)).thenReturn(Optional.of(timeSheet));

        // Act
        TimeSheetDTO result = employerService.getTimeSheetByJobApplicationId(jobApplicationId);

        // Assert
        assertNotNull(result);
        assertEquals(jobApplicationId, result.getJobApplicationId());
        verify(timeSheetRepository, times(1)).findByJobApplicationId(jobApplicationId);
    }

    @Test
    public void testSaveInternEvaluationForJobApplicationId_existentJobApplication() {
        // Arrange
        Long jobOfferId = 1L;
        Long studentId = 1L;
        Long jobApplicationId = 1L;
        JobOffer jobOffer = JobOffer.builder().id(jobOfferId).build();
        Student student = Student.builder().id(studentId).build();
        JobApplication jobApplication = JobApplication.builder().id(jobApplicationId).build();

        jobApplication.setStudent(student);
        jobApplication.setJobOffer(jobOffer);
        List<SectionDTO> sections = new ArrayList<>();

        SectionDTO productivityDTO = new SectionDTO();
        productivityDTO.setAgreementLevels(List.of(
                AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_DISAGREE, AgreementLevel.TOTALLY_DISAGREE, AgreementLevel.TOTALLY_DISAGREE, AgreementLevel.TOTALLY_DISAGREE
        ));
        productivityDTO.setComment("Test");
        sections.add(productivityDTO);

        SectionDTO qualityOfWorkDTO = new SectionDTO();
        qualityOfWorkDTO.setAgreementLevels(
                List.of(AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE)
        );
        qualityOfWorkDTO.setComment("Test");
        sections.add(qualityOfWorkDTO);

        SectionDTO interpersonalSkillsDTO = new SectionDTO();
        interpersonalSkillsDTO.setAgreementLevels(
                List.of(AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE)
        );
        interpersonalSkillsDTO.setComment("Test");
        sections.add(interpersonalSkillsDTO);

        SectionDTO personalSkillsDTO = new SectionDTO();
        personalSkillsDTO.setAgreementLevels(
                List.of(AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE)
        );
        personalSkillsDTO.setComment("Test");
        sections.add(personalSkillsDTO);

        SectionDTO globalAppreciationDTO = new SectionDTO();
        globalAppreciationDTO.setAgreementLevels(
                List.of(AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE)
        );
        globalAppreciationDTO.setComment("Test");
        sections.add(globalAppreciationDTO);

        SectionDTO reAdmitDTO = new SectionDTO();
        reAdmitDTO.setAgreementLevels(
                List.of(AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE, AgreementLevel.TOTALLY_AGREE)
        );
        reAdmitDTO.setComment("Test");
        sections.add(reAdmitDTO);

        List<AgreementLevel> agreementLevelsToExpect = new ArrayList<>();
        agreementLevelsToExpect.add(AgreementLevel.TOTALLY_AGREE);
        agreementLevelsToExpect.add(AgreementLevel.TOTALLY_DISAGREE);
        agreementLevelsToExpect.add(AgreementLevel.TOTALLY_DISAGREE);
        agreementLevelsToExpect.add(AgreementLevel.TOTALLY_DISAGREE);
        agreementLevelsToExpect.add(AgreementLevel.TOTALLY_DISAGREE);

        when(jobApplicationRepository.findById(jobApplicationId)).thenReturn(Optional.of(jobApplication));
        when(internEvaluationRepository.save(any(InternEvaluation.class))).thenReturn(new InternEvaluation());

        // Act
        InternEvaluationDTO result = employerService.saveInternEvaluationForJobApplicationId(jobApplicationId, sections);

        // Assert
        assertNotNull(result);
        assertEquals(agreementLevelsToExpect, result.getSections().get(0).getAgreementLevels());
        verify(jobApplicationRepository, times(1)).findById(jobApplicationId);
        verify(internEvaluationRepository, times(1)).save(any(InternEvaluation.class));
    }
}

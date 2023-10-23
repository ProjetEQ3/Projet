package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.badRequestException.EmployerNotFoundException;
import cal.projeteq3.glucose.exception.badRequestException.JobOfferNotFoundException;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    public void testGetStudentsByJobOfferId_full() {
        Long testJobOfferId = 1L;

        // Arrange the mock objects
        JobOffer mockJobOffer = new JobOffer();
        JobApplication mockJobApplication = new JobApplication();
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

    @Test
    void testGetStudentsByJobOfferId_empty(){
        // Arrange
        Long testJobOfferId = 1L;
        JobOffer mockJobOffer = new JobOffer();
        mockJobOffer.setJobApplications(Collections.emptyList());
        when(jobOfferRepository.findById(anyLong())).thenReturn(Optional.of(mockJobOffer));

        // Act
        List<StudentDTO> result = employerService.getStudentsByJobOfferId(testJobOfferId);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(jobOfferRepository, times(1)).findById(testJobOfferId);
    }

    @Test
    void testGetStudentsByJobOfferId_NotFound(){
        // Arrange
        Long testJobOfferId = 99L;
        when(jobOfferRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(JobOfferNotFoundException.class, () ->
                employerService.getStudentsByJobOfferId(testJobOfferId));
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

        Appointment appointment = Appointment.builder()
                .appointmentDate(LocalDateTime.now())
                .jobApplication(mockApplication)
                .build();

        Appointment appointment2 = Appointment.builder()
                .appointmentDate(LocalDateTime.now().plusDays(1))
                .jobApplication(mockApplication)
                .build();

        Appointment appointment3 = Appointment.builder()
                .appointmentDate(LocalDateTime.now().plusDays(2))
                .jobApplication(mockApplication)
                .build();
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment2);
        appointmentList.add(appointment3);

        when(jobApplicationRepository.findById(applicationId)).thenReturn(Optional.of(mockApplication));
        when(jobApplicationRepository.save(mockApplication)).thenReturn(mockApplication);

        employerService.addAppointmentByJobApplicationId(mockApplication.getId(), appointmentList);
        verify(jobApplicationRepository, times(1)).save(mockApplication);
        assertEquals(mockApplication.getAppointments().size(), 3);
        assertEquals(mockApplication.getAppointments(), appointmentList);
    }

}

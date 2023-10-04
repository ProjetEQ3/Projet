package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.contract.CreateContractDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.request.AddressNotFoundException;
import cal.projeteq3.glucose.exception.request.EmployerNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.exception.request.SupervisorNotFoundException;
import cal.projeteq3.glucose.model.Address;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.contract.EmploymentType;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.Supervisor;
import cal.projeteq3.glucose.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private SupervisorRepository supervisorRepository;
    @Mock
    private ContractRepository contractRepository;
    @InjectMocks
    private EmployerService employerService;


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
                                .startDate(LocalDateTime.now())
                                .expirationDate(LocalDateTime.now().plusDays(30))
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
                                .startDate(LocalDateTime.now())
                                .expirationDate(LocalDateTime.now().plusDays(30))
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
                                .startDate(LocalDateTime.now().minusDays(60))
                                .expirationDate(LocalDateTime.now().minusDays(30))
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

//        Assert
        assertEquals(jobOfferDTOS.size(), jobOffers.size());
        verify(employerRepository, times(1)).findById(id);
    }

    @Test
    void getJobOffersDTOByEmployerId_invalidId(){
//        Arrange
        Long id = 1L;

        when(employerRepository.findById(id)).thenReturn(Optional.empty());

//        Act & Assert
        assertThrows(EmployerNotFoundException.class, () ->
                employerService.getJobOffersDTOByEmployerId(id));

        verify(employerRepository, times(1)).findById(id);
    }

    @Test
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
                LocalDateTime.now(),
                6,
                LocalDateTime.now().plusDays(30),
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
                        .startDate(LocalDateTime.now())
                        .duration(6)
                        .expirationDate(LocalDateTime.now().plusDays(30))
                        .jobOfferState(JobOfferState.OPEN)
                        .hoursPerWeek(40)
                        .build(),
                JobOffer.builder()
                        .title("JobOffer2")
                        .department(Department._420B0)
                        .location("Location1")
                        .description("Description1")
                        .salary(20.0f)
                        .startDate(LocalDateTime.now())
                        .duration(6)
                        .expirationDate(LocalDateTime.now().plusDays(30))
                        .jobOfferState(JobOfferState.OPEN)
                        .hoursPerWeek(40)
                        .build(),
                JobOffer.builder()
                        .title("JobOffer3")
                        .department(Department._420B0)
                        .location("Location1")
                        .description("Description1")
                        .salary(20.0f)
                        .startDate(LocalDateTime.now())
                        .duration(6)
                        .expirationDate(LocalDateTime.now().plusDays(30))
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
    }

//    -------------- Contract --------------

    @Test
    void createContract_valid(){
//        Arrange
        Long jobOfferId = 1L;
        Long supervisorId = 1L;
        Long addressId = 1L;

        CreateContractDTO createContractDTO = new CreateContractDTO(
                jobOfferId,
                addressId,
                supervisorId,
                List.of("responsibility 1", "responsibility 2"),
                LocalDate.now(),
                LocalDate.now(),
                1,
                Time.valueOf("08:00:00"),
                Time.valueOf("16:00:00"),
                1,
                EmploymentType.APPRENTICESHIP,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)
        );

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(
                Optional.of(JobOffer.builder()
                        .id(jobOfferId)
                        .employer(new Employer())
                        .jobApplications(new ArrayList<>(Set.of(
                                JobApplication.builder()
                                        .student(new Student())
                                        .jobApplicationState(JobApplicationState.ACCEPTED)
                                        .build()
                        )))
                        .build())
        );
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(new Supervisor()));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(new Address()));
        when(contractRepository.save(any())).thenReturn(Contract.builder()
                        .employer(new Employer())
                        .supervisor(new Supervisor())
                        .workAddress(new Address())
                        .student(new Student())
                        .responsibilities(createContractDTO.getResponsibilities())
                        .startDate(createContractDTO.getStartDate())
                        .endDate(createContractDTO.getEndDate())
                        .duration(createContractDTO.getDuration())
                        .startShiftTime(createContractDTO.getStartShiftTime())
                        .endShiftTime(createContractDTO.getEndShiftTime())
                        .hoursPerDay(createContractDTO.getHoursPerDay())
                        .employmentType(createContractDTO.getEmploymentType())
                        .workDays(createContractDTO.getWorkDays())
                        .hourlyRate(20.0f)
                .build());

//        Act
        ContractDTO result = employerService.createContract(createContractDTO);

//        Assert
        assertNotNull(result);
        assertEquals(createContractDTO.getResponsibilities(), result.getResponsibilities());
        assertEquals(createContractDTO.getStartDate(), result.getStartDate());
        assertEquals(createContractDTO.getEndDate(), result.getEndDate());
        assertEquals(createContractDTO.getDuration(), result.getDuration());
        assertEquals(createContractDTO.getStartShiftTime(), result.getStartShiftTime());
        assertEquals(createContractDTO.getEndShiftTime(), result.getEndShiftTime());
        assertEquals(createContractDTO.getHoursPerDay(), result.getHoursPerDay());
        assertEquals(createContractDTO.getEmploymentType(), result.getEmploymentType());
        assertEquals(createContractDTO.getWorkDays(), result.getWorkDays());
        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        verify(supervisorRepository, times(1)).findById(supervisorId);
        verify(addressRepository, times(1)).findById(addressId);
    }

    @Test
    void createContract_invalidJobOfferId(){
//        Arrange
        Long jobOfferId = 1L;
        Long supervisorId = 1L;
        Long addressId = 1L;

        CreateContractDTO createContractDTO = new CreateContractDTO(
                jobOfferId,
                addressId,
                supervisorId,
                List.of("responsibility 1", "responsibility 2"),
                LocalDate.now(),
                LocalDate.now(),
                1,
                Time.valueOf("08:00:00"),
                Time.valueOf("16:00:00"),
                1,
                EmploymentType.APPRENTICESHIP,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)
        );

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.empty());

//        Act & Assert
        assertThrows(JobOffreNotFoundException.class, () ->
                employerService.createContract(createContractDTO));

        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        verify(supervisorRepository, times(0)).findById(supervisorId);
        verify(addressRepository, times(0)).findById(addressId);
        verify(contractRepository, times(0)).save(any());
    }

    @Test
    void createContract_invalidSupervisorId(){
//        Arrange
        Long jobOfferId = 1L;
        Long supervisorId = 1L;
        Long addressId = 1L;

        CreateContractDTO createContractDTO = new CreateContractDTO(
                jobOfferId,
                addressId,
                supervisorId,
                List.of("responsibility 1", "responsibility 2"),
                LocalDate.now(),
                LocalDate.now(),
                1,
                Time.valueOf("08:00:00"),
                Time.valueOf("16:00:00"),
                1,
                EmploymentType.APPRENTICESHIP,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)
        );

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(
                Optional.of(JobOffer.builder()
                        .id(jobOfferId)
                        .employer(new Employer())
                        .jobApplications(new ArrayList<>(Set.of(
                                JobApplication.builder()
                                        .student(new Student())
                                        .jobApplicationState(JobApplicationState.ACCEPTED)
                                        .build()
                        )))
                        .build())
        );
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.empty());

//        Act & Assert
        assertThrows(SupervisorNotFoundException.class, () ->
                employerService.createContract(createContractDTO));

        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        verify(supervisorRepository, times(1)).findById(supervisorId);
        verify(addressRepository, times(0)).findById(addressId);
        verify(contractRepository, times(0)).save(any());
    }

    @Test
    void createContract_invalidAddressId(){
//        Arrange
        Long jobOfferId = 1L;
        Long supervisorId = 1L;
        Long addressId = 1L;

        CreateContractDTO createContractDTO = new CreateContractDTO(
                jobOfferId,
                addressId,
                supervisorId,
                List.of("responsibility 1", "responsibility 2"),
                LocalDate.now(),
                LocalDate.now(),
                1,
                Time.valueOf("08:00:00"),
                Time.valueOf("16:00:00"),
                1,
                EmploymentType.APPRENTICESHIP,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)
        );

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(
                Optional.of(JobOffer.builder()
                        .id(jobOfferId)
                        .employer(new Employer())
                        .jobApplications(new ArrayList<>(Set.of(
                                JobApplication.builder()
                                        .student(new Student())
                                        .jobApplicationState(JobApplicationState.ACCEPTED)
                                        .build()
                        )))
                        .build())
        );
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(new Supervisor()));
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

//        Act & Assert
        assertThrows(AddressNotFoundException.class, () ->
                employerService.createContract(createContractDTO));

        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        verify(supervisorRepository, times(1)).findById(supervisorId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(contractRepository, times(0)).save(any());
    }

    @Test
    void createContract_invalidJobApplicationState(){
//        Arrange
        Long jobOfferId = 1L;
        Long supervisorId = 1L;
        Long addressId = 1L;

        CreateContractDTO createContractDTO = new CreateContractDTO(
                jobOfferId,
                addressId,
                supervisorId,
                List.of("responsibility 1", "responsibility 2"),
                LocalDate.now(),
                LocalDate.now(),
                1,
                Time.valueOf("08:00:00"),
                Time.valueOf("16:00:00"),
                1,
                EmploymentType.APPRENTICESHIP,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)
        );

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(
                Optional.of(JobOffer.builder()
                        .id(jobOfferId)
                        .employer(new Employer())
                        .jobApplications(new ArrayList<>(Set.of(
                                JobApplication.builder()
                                        .student(new Student())
                                        .jobApplicationState(JobApplicationState.SUBMITTED)
                                        .build()
                        )))
                        .build())
        );
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(new Supervisor()));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(new Address()));

//        Act & Assert

        assertThrows(NoSuchElementException.class, () ->
                employerService.createContract(createContractDTO));

        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        verify(supervisorRepository, times(1)).findById(supervisorId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(contractRepository, times(0)).save(any());
    }

}

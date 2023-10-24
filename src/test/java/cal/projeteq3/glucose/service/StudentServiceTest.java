package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.exception.badRequestException.JobOfferNotFoundException;
import cal.projeteq3.glucose.exception.badRequestException.StudentNotFoundException;
import cal.projeteq3.glucose.exception.unauthorizedException.CvNotApprovedException;
import cal.projeteq3.glucose.exception.unauthorizedException.JobOfferNotOpenException;
import cal.projeteq3.glucose.exception.unauthorizedException.StudentHasAlreadyAppliedException;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.exception.unauthorizedException.StudentHasAlreadyCVException;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.CvFileRepository;
import cal.projeteq3.glucose.repository.JobApplicationRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CvFileRepository cvFileRepository;
    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @InjectMocks
    private StudentService studentService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private JobOffer jobOffer;

    private Student student;

    @BeforeEach
    void setUp() {
        jobOffer = new JobOffer();
        student = mock(Student.class);
    }

    @Test
    public void createStudent_Valid() {

        //Arrange
        RegisterStudentDTO registerStudentDTO =
                new RegisterStudentDTO(
                    new RegisterDTO(
                            "test@tester.com",
                            "Test1234",
                            "STUDENT"),
                    StudentDTO.builder()
                            .firstName("Michel")
                            .lastName("Michaud")
                            .matricule("1234567")
                            .department("_420B0")
                            .build()
                );

        Student student = Student.builder()
                .firstName("Michel")
                .lastName("Michaud")
                .email("test@tester.com")
                .password("Test1234")
                .matricule("1234567")
                .department("_420B0")
                .build();


        when(studentRepository.save(student))
                .thenReturn(Student.builder()
                    .id(1L)
                    .firstName("Michel")
                    .lastName("Michaud")
                    .email("test@tester.com")
                    .password("Test1234")
                    .matricule("1234567")
                    .department("_420B0")
                    .build()
        );
        when(passwordEncoder.encode("Test1234")).thenReturn("Test1234");

        //Act
        studentService.createStudent(registerStudentDTO);

        //Assert
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void getAllStudents_() {

        //Arrange
        List<Student> students = new ArrayList<>();
        Student student1 = Student.builder()
                .firstName("Michel")
                .lastName("Michaud")
                .email("test@tester.com")
                .password("Test1234")
                .matricule("1234567")
                .department("_420B0")
                .build();

        Student student2 = Student.builder()
                .firstName("Michel")
                .lastName("Michaud")
                .email("test@tester.com")
                .password("Test1234")
                .matricule("1234567")
                .department("_420B0")
                .build();

        students.add(student1);
        students.add(student2);

        when(studentRepository.findAll()).thenReturn(students);

        //Act
        List<StudentDTO> studentList = studentService.getAllStudents();

        //Assert
        assertEquals(2, studentList.size());
        verify(studentRepository, times(1)).findAll();

    }

    @Test
    public void getStudentByID_valid_id() {
        // Arrange
        Long id = 1L;
        Student student = Student.builder()
                .firstName("Michel")
                .lastName("Michaud")
                .email("test@tester.com")
                .password("Test1234")
                .matricule("1234567")
                .department("_420B0")
                .build();

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // Act
        StudentDTO studentDTO = studentService.getStudentByID(id);

        // Assert

        assertEquals(student.getId(), studentDTO.getId());
        assertEquals(student.getFirstName(), studentDTO.getFirstName());
        assertEquals(student.getLastName(), studentDTO.getLastName());
        assertEquals(student.getEmail(), studentDTO.getEmail());
        assertEquals(student.getMatricule(), studentDTO.getMatricule());
        assertEquals(student.getDepartment(), studentDTO.getDepartment());

        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    public void getStudentByID_nonExistent_id() {
        // Arrange
        Long nonExistentId = 999L;
        when(studentRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentByID(nonExistentId);
        });

        verify(studentRepository, times(1)).findById(nonExistentId);
    }

    @Test
    public void getStudentByID_invalid_negative_id() {
        // Arrange
        Long negativeId = -1L;
        when(studentRepository.findById(negativeId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentByID(negativeId);
        });

        verify(studentRepository, times(1)).findById(negativeId);
    }


    @Test
    public void updateStudent_valid() {

        // Arrange
        Long studentId = 1L;
        StudentDTO updatedStudent = new StudentDTO();
        updatedStudent.setId(studentId);
        updatedStudent.setFirstName("UpdatedFirstName");
        updatedStudent.setLastName("UpdatedLastName");
        updatedStudent.setEmail("updated@example.com");
        updatedStudent.setMatricule("UpdatedMatricule");
        updatedStudent.setDepartment(Department._420B0);

        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstName("OriginalFirstName");
        existingStudent.setLastName("OriginalLastName");
        existingStudent.setCredentials(
                Credentials.builder()
                        .email("original@example.com")
                        .password("OriginalPassword")
                        .role(Role.STUDENT)
                        .build());
        existingStudent.setMatricule("OriginalMatricule");
        existingStudent.setDepartment(Department._410B0);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        // Act
        StudentDTO updatedDTO = studentService.updateStudent(studentId, updatedStudent);

        // Assert
        assertNotNull(updatedDTO);
        assertEquals(updatedStudent.getId(), updatedDTO.getId());
        assertEquals(updatedStudent.getFirstName(), updatedDTO.getFirstName());
        assertEquals(updatedStudent.getLastName(), updatedDTO.getLastName());
        assertEquals(updatedStudent.getEmail(), updatedDTO.getEmail());
        assertEquals(updatedStudent.getMatricule(), updatedDTO.getMatricule());
        assertEquals(updatedStudent.getDepartment(), updatedDTO.getDepartment());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    public void updateStudent_nonExistentStudent() {
        // Arrange
        Long nonExistentStudentId = 999L;
        StudentDTO updatedStudent = null;

        when(studentRepository.findById(nonExistentStudentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.updateStudent(nonExistentStudentId, updatedStudent);
        });
        verify(studentRepository, times(1)).findById(nonExistentStudentId);
    }

    @Test
    public void updateStudent_invalidStudentId() {
        // Arrange
        Long invalidStudentId = -1L;
        StudentDTO updatedStudent = null;

        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.updateStudent(invalidStudentId, updatedStudent);
        });
        verify(studentRepository, times(1)).findById(invalidStudentId);
    }

    @Test
    public void deleteStudent_existing() {

        // Arrange
        Long studentId = 1L;
        doNothing().when(studentRepository).deleteById(studentId);

        // Act
        studentService.deleteStudent(studentId);

        // Assert
        verify(studentRepository, times(1)).deleteById(studentId);

    }

    @Test
    public void GetJobOffersByDepartment_Valid() {

        // Arrange

        Semester semester = new Semester(LocalDate.now());
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
                                .semester(semester)
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
                                .semester(semester)
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
                                .semester(semester)
                                .build()
                )
        );

        when(jobOfferRepository.findJobOffersByDepartmentAndSemester(Department._420B0, semester)).thenReturn(jobOffers_420B0);

//        Act
        List<JobOfferDTO> jobOffers = studentService.getJobOffersByDepartment(Department._420B0, semester);

//        Assert
        assertEquals(3, jobOffers.size());
        verify(jobOfferRepository, times(1)).findJobOffersByDepartmentAndSemester(Department._420B0, semester);

    }

    @Test
    public void GetOpenJobOffersByDepartment_Valid() {

        // Arrange

        Semester semester = new Semester(LocalDate.now());
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
                                .semester(semester)
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
                                .semester(semester)
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
                                .semester(semester)
                                .build()
                )
        );

        when(jobOfferRepository
                .findJobOffersByDepartmentAndJobOfferStateAndSemester(Department._420B0, JobOfferState.OPEN, semester))
                .thenReturn(jobOffers_420B0.stream()
                        .filter(jobOffer -> jobOffer.getJobOfferState()
                                .equals(JobOfferState.OPEN))
                        .collect(Collectors.toList()));

//        Act
        List<JobOfferDTO> jobOffers = studentService.getOpenJobOffersByDepartment(Department._420B0, semester);

//        Assert
        assertEquals(1, jobOffers.size());
        verify(jobOfferRepository, times(1)).findJobOffersByDepartmentAndJobOfferStateAndSemester(Department._420B0, JobOfferState.OPEN, semester);

    }

    @Test
    void testApplyJobOffer_JobOfferNotFound() {
        when(jobOfferRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(JobOfferNotFoundException.class, () -> {
            studentService.applyJobOffer(1L, 1L);
        });
    }

    @Test
    void testApplyJobOffer_StudentNotFound() {
        JobOffer jobOffer = new JobOffer();
        when(jobOfferRepository.findById(1L)).thenReturn(Optional.of(jobOffer));
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.applyJobOffer(1L, 1L);
        });
    }

    @Test
    void applyJobOffer_SuccessfulApplication() {
        // Arrange
        Long jobOfferId = 1L;
        Long studentId = 2L;
        JobOffer jobOffer = JobOffer.builder()
                .id(jobOfferId)
                .jobOfferState(JobOfferState.OPEN)
                .jobApplications(new ArrayList<>())
                .build();
        Student student = Student.builder()
                .id(studentId)
                .cvFile(CvFile.builder()
                        .fileName("cv.pdf")
                        .cvState(CvState.ACCEPTED)
                        .build())
                .build();

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.of(jobOffer));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jobOfferRepository.save(any(JobOffer.class))).thenReturn(jobOffer);

        // Act
        JobOfferDTO result = studentService.applyJobOffer(jobOfferId, studentId);

        // Assert
        assertNotNull(result);
        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        verify(studentRepository, times(1)).findById(studentId);
        verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
    }

    @Test
    void applyJobOffer_StudentAlreadyApplied() {
        // Arrange
        Long jobOfferId = 1L;
        Long studentId = 2L;
        JobOffer jobOffer = JobOffer.builder()
                .id(jobOfferId)
                .jobOfferState(JobOfferState.OPEN)
                .jobApplications(new ArrayList<>())
                .build();
        Student student = Student.builder()
                .id(studentId)
                .cvFile(CvFile.builder()
                        .fileName("cv.pdf")
                        .cvState(CvState.ACCEPTED)
                        .build())
                .build();
        jobOffer.addJobApplication(new JobApplication(1L, JobApplicationState.SUBMITTED, student, jobOffer, new Semester(LocalDate.now()), new ArrayList<>(0)));

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.of(jobOffer));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act and Assert
        assertThrows(StudentHasAlreadyAppliedException.class, () -> studentService.applyJobOffer(jobOfferId, studentId));
    }

    @Test
    void applyJobOffer_CvNotApproved() {
        // Arrange
        Long jobOfferId = 1L;
        Long studentId = 2L;
        JobOffer jobOffer = new JobOffer();
        Student student = new Student();

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.of(jobOffer));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act and Assert
        assertThrows(CvNotApprovedException.class, () -> studentService.applyJobOffer(jobOfferId, studentId));
    }

    @Test
    void applyJobOffer_JobOfferNotOpen() {
        // Arrange
        Long jobOfferId = 1L;
        Long studentId = 2L;
        JobOffer jobOffer = new JobOffer();
        Student student = new Student();

        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.of(jobOffer));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act and Assert
        assertThrows(CvNotApprovedException.class, () -> studentService.applyJobOffer(jobOfferId, studentId));
    }


    @Test
    void apply_withApprovedCVAndOpenJobOffer_returnsJobApplication() {
        when(student.hasApprovedCv()).thenReturn(true);
        jobOffer.setJobOfferState(JobOfferState.OPEN);

        JobApplication result = jobOffer.apply(student);

        assertNotNull(result);
        assertEquals(student, result.getStudent());
        assertEquals(jobOffer, result.getJobOffer());
    }

    @Test
    void apply_withNonApprovedCV_throwsCvNotApprovedException() {
        when(student.hasApprovedCv()).thenReturn(false);
        jobOffer.setJobOfferState(JobOfferState.OPEN);

        assertThrows(CvNotApprovedException.class, () -> jobOffer.apply(student));
    }

    @Test
    void apply_withClosedJobOffer_throwsJobOfferNotOpenException() {
        when(student.hasApprovedCv()).thenReturn(true);
        jobOffer.setJobOfferState(JobOfferState.SUBMITTED);  // Or any state other than OPEN

        assertThrows(JobOfferNotOpenException.class, () -> jobOffer.apply(student));
    }

    @Test
    void apply_withNonApprovedCVAndClosedJobOffer_throwsCvNotApprovedException() {
        // We're prioritizing the CV check first as per the `apply` method logic.
        when(student.hasApprovedCv()).thenReturn(false);
        jobOffer.setJobOfferState(JobOfferState.SUBMITTED);  // Or any state other than OPEN

        assertThrows(CvNotApprovedException.class, () -> jobOffer.apply(student));
    }

    @Test
    public void addCv_valid() {
        // Arrange
        Long studentId = 1L;
        CvFileDTO cvFileDTO = new CvFileDTO(
                1L,
                "cv.pdf",
                new byte[]{1,1,1,1,1,1,1,1,1,1,1},
                CvState.SUBMITTED,
                null);

        Student student = Student.builder()
                .id(studentId)
                .build();
        Student studentWithCv = Student.builder()
                .id(studentId)
                .cvFile(cvFileDTO.toEntity())
                .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(studentWithCv);

        // Act
        CvFileDTO addedCv = studentService.addCv(studentId, cvFileDTO);

        // Assert
        assertNotNull(addedCv);
        assertEquals(cvFileDTO.getFileName(), addedCv.getFileName());
        assertEquals(cvFileDTO.getFileData(), addedCv.getFileData());
        assertEquals(cvFileDTO.getCvState(), addedCv.getCvState());
        assertEquals(cvFileDTO.getRefusReason(), addedCv.getRefusReason());
    }

    @Test
    public void addCv_studentNotFound() {
        // Arrange
        Long nonExistentStudentId = 999L;
        CvFileDTO cvFileDTO = new CvFileDTO();

        when(studentRepository.findById(nonExistentStudentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.addCv(nonExistentStudentId, cvFileDTO);
        });
        verify(studentRepository, times(1)).findById(nonExistentStudentId);
    }

    @Test
    public void addCv_studentAlreadyHasCv() {
        // Arrange
        Long studentId = 1L;
        CvFileDTO cvFileDTO = new CvFileDTO();

        Student student = new Student();
        student.setCvFile(new CvFile());

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act and Assert
        assertThrows(StudentHasAlreadyCVException.class, () -> {
            studentService.addCv(studentId, cvFileDTO);
        });
        verify(studentRepository, times(1)).findById(studentId);
    }



    @Test
    public void deleteCv_valid() {
        // Arrange
        Long studentId = 1L;
        Student student = new Student();
        CvFile cvFile = new CvFile();
        student.setCvFile(cvFile);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act
        studentService.deleteCv(studentId);

        // Assert
        assertNull(student.getCvFile());
        verify(cvFileRepository, times(1)).delete(cvFile);
    }

    @Test
    public void deleteCv_studentNotFound() {
        // Arrange
        Long nonExistentStudentId = 999L;

        when(studentRepository.findById(nonExistentStudentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.deleteCv(nonExistentStudentId);
        });
        verify(studentRepository, times(1)).findById(nonExistentStudentId);
    }

    @Test
    public void getAppliedJobOfferByStudentId_valid() {
        // Arrange
        Long studentId = 1L;
        Semester semester = new Semester(LocalDate.now());
        Student student = Student.builder()
                .id(studentId)
                .build();

        JobApplication jobApplication = JobApplication.builder()
                .student(student)
                .semester(semester)
                .build();

        JobOffer jobOffer = JobOffer.builder()
                .id(1L)
                .jobApplications(List.of(jobApplication))
                .semester(semester)
                .build();

        when(jobOfferRepository.findAppliedJobOffersByStudent_Id(studentId, semester)).thenReturn(List.of(jobOffer));

        jobOfferRepository.save(jobOffer);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act

        List<JobOfferDTO> appliedOffers = studentService.getAppliedJobOfferByStudentId(studentId, semester);

        // Assert
        assertEquals(jobOffer.getId(), appliedOffers.get(0).toEntity().getId());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    public void getAppliedJobOfferByStudentId_studentNotFound() {
        // Arrange
        Long notFoundStudentId = -1L;
        Semester semester = new Semester(LocalDate.now());

        when(studentRepository.findById(notFoundStudentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getAppliedJobOfferByStudentId(notFoundStudentId, semester);
        });
        verify(studentRepository, times(1)).findById(notFoundStudentId);

    }

    @Test
    public void getCv_Valid() {
        // Arrange
        Long studentId = 1L;
        Student student = Student.builder()
                .id(studentId)
                .build();

        CvFile cvFile = CvFile.builder()
                .fileName("cv.pdf")
                .fileData(new byte[]{1,1,1,1,1,1,1,1,1,1,1})
                .cvState(CvState.SUBMITTED)
                .build();

        student.setCvFile(cvFile);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act
        CvFileDTO cvFileDTO = studentService.getCv(studentId);

        // Assert
        assertEquals(cvFile.getFileName(), cvFileDTO.getFileName());
        assertEquals(cvFile.getFileData(), cvFileDTO.getFileData());
        assertEquals(cvFile.getCvState(), cvFileDTO.getCvState());
        assertEquals(cvFile.getRefusReason(), cvFileDTO.getRefusReason());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    public void getCv_studentNotFound() {
        // Arrange
        Long notFoundStudentId = -1L;

        when(studentRepository.findById(notFoundStudentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getCv(notFoundStudentId);
        });
        verify(studentRepository, times(1)).findById(notFoundStudentId);
    }

    @Test
    public void getAppointmentsByJobApplicationIdTest() {

        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(1L);
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setJobApplication(jobApplication);
        Appointment appointment2 = new Appointment();
        appointment2.setJobApplication(jobApplication);

        when(jobApplicationRepository.findAppointmentsByJobApplicationId(any(Long.class)))
                .thenReturn(appointments);

        List<AppointmentDTO> result = studentService.getAppointmentsByJobApplicationId(jobApplication.getId());

        assertEquals(appointments.size(), result.size());

    }

}

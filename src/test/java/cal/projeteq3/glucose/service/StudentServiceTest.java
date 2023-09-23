package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private JobOfferRepository jobOfferRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void createStudentTest() {

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

        //Act
        studentService.createStudent(registerStudentDTO);

        //Assert
        verify(studentRepository, times(1)).save(student);

    }

    @Test
    public void getAllStudentsTest() {

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
    public void getStudentByIDTest() {

        //Arrange
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

        //Act
        Optional<StudentDTO> studentDTO = studentService.getStudentByID(id);

        //Assert
        assertTrue(studentDTO.isPresent());
        verify(studentRepository, times(1)).findById(id);

    }

    @Test
    public void updateStudentTest() {

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
        existingStudent.setEmail("original@example.com");
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

    }

    @Test
    public void deleteStudentTest() {

        // Arrange
        Long studentId = 1L;
        doNothing().when(studentRepository).deleteById(studentId);

        // Act
        studentService.deleteStudent(studentId);

        // Assert
        verify(studentRepository, times(1)).deleteById(studentId);

    }

    @Test
    public void getJobOffersByDepartmentTest() {

        // Arrange
        Student student1 = Student.builder()
                .firstName("Michel")
                .lastName("Michaud")
                .email("test@test.com")
                .password("Test1234")
                .matricule("1234567")
                .department("_420B0")
                .build();

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

        when(jobOfferRepository.findJobOffersByDepartment(Department._420B0)).thenReturn(jobOffers_420B0);

//        Act
        List<JobOfferDTO> jobOffers = studentService.getJobOffersByDepartment(Department._420B0);

//        Assert
        assertEquals(3, jobOffers.size());
        verify(jobOfferRepository, times(1)).findJobOffersByDepartment(Department._420B0);


    }

}

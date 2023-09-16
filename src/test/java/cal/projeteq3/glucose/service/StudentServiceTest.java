package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private StudentService studentService;

    @Test
    public void createStudentTest() {

        //Arrange

        Student student = new Student("Michel", "Michaud", "T@T.com", "Ose12asd3", "1234567", "_420B0", null);
        when(studentRepository.save(student)).thenReturn(new Student("Michel", "Michaud", "T@T.com", "Ose12asd3", "1234567", "_420B0", null));

        //Act

        //studentService.createStudent(student);

        //Assert

        verify(studentRepository, times(1)).save(student);

    }

    @Test
    public void getAllStudentsTest() {

        //Arrange

        List<Student> students = new ArrayList<>();
        Student student1 = new Student("Michel1", "Michaud1", "T@T1.com", "Ose12asd3", "12345671", "_420B0", null);
        Student student2 = new Student("Michel2", "Michaud2", "T@T2.com", "Ose12asd3", "12345672", "_420B0", null);
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
        Student student = new Student("Michel", "Michaud", "T@T.com", "Ose12asd3", "1234567", "_420B0", null);
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
        existingStudent.setUserID(studentId);
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

}

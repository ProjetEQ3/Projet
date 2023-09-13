package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.StudentDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        studentService.createStudent(student);

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

        //Act

        //Assert

    }

    @Test
    public void updateStudentTest() {

        //Arrange

        //Act

        //Assert

    }

    @Test
    public void deleteStudentTest() {

        //Arrange

        //Act

        //Assert

    }

}

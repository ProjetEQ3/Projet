package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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

        //Act

        //Assert

    }

}

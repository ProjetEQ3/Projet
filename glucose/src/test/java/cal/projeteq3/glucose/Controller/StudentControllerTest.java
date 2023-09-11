package cal.projeteq3.glucose.Controller;

import cal.projeteq3.glucose.controller.StudentController;
import cal.projeteq3.glucose.dto.StudentDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentControllerTest {

    private StudentController studentController;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = mock(StudentService.class);
        studentController = new StudentController(studentService);
    }

    @Test
    void Register_Valid() {
        // Arrange
        Student validStudent = new Student("Michel", "Michaud", "T@T.com", "Ose12asd3", "1234567", "_420B0", null);

        when(studentService.createStudent(validStudent)).thenReturn(new StudentDTO("1234567", Department._420B0));

        // Act
        ResponseEntity<StudentDTO> responseEntity = studentController.register(validStudent);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void Register_MissingFirstName(){
//        Arrange
        Student invalidStudent = new Student(
                "",
                "Michaud",
                "T@T.com",
                "Ose12asd3",
                "1234567",
                "_420B0",
                null);

//        Act
        ResponseEntity<StudentDTO> responseEntity = studentController.register(invalidStudent);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void Register_MissingLastName(){
//        Arrange
        Student invalidStudent = new Student(
                "Michel",
                "",
                "T@T.com",
                "Ose12asd3",
                "1234567",
                "_420B0",
                null);

//        Act
        ResponseEntity<StudentDTO> responseEntity = studentController.register(invalidStudent);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void Register_MissingEmail(){
//        Arrange
        Student invalidStudent = new Student(
                "Michel",
                "Michaud",
                "",
                "Ose12asd3",
                "1234567",
                "_420B0",
                null);

//        Act
        ResponseEntity<StudentDTO> responseEntity = studentController.register(invalidStudent);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void Register_MissingPassword(){
//        Arrange
        Student invalidStudent = new Student(
                "Michel",
                "Michaud",
                "T@T.com",
                "",
                "1234567",
                "_420B0",
                null);

//        Act
        ResponseEntity<StudentDTO> responseEntity = studentController.register(invalidStudent);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void Register_MissingMatricule(){
//        Arrange
        Student invalidStudent = new Student(
                "Michel",
                "Michaud",
                "T@T.com",
                "Ose12asd3",
                "",
                "_420B0",
                null);

//        Act
        ResponseEntity<StudentDTO> responseEntity = studentController.register(invalidStudent);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void Register_MissingDepartement(){
//        Arrange
        Student invalidStudent = new Student(
                "Michel",
                "Michaud",
                "T@T.com",
                "Ose12asd3",
                "1234567",
                "_420A0",
                null);

//        Act
        ResponseEntity<StudentDTO> responseEntity = studentController.register(invalidStudent);

//        Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }
}

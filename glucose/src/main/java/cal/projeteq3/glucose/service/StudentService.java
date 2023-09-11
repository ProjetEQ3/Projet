package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // database operations here

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByID(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if(existingStudent.isPresent()) {
            Student student = existingStudent.get();

            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setPassword(updatedStudent.getPassword());
            student.setMatricule(updatedStudent.getMatricule());
            student.setDepartment(updatedStudent.getDepartment());

            return studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Student with ID " + id + " does not exist.");
        }
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}

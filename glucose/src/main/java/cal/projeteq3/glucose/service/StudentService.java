package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.StudentDTO;
import cal.projeteq3.glucose.mapper.StudentMapper;
import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    // database operations here

    public StudentDTO createStudent(Student student) {
        return studentMapper.toDTO(studentRepository.save(student));
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<StudentDTO> getStudentByID(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.map(studentMapper::toDTO);
    }

    public StudentDTO updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if(existingStudent.isPresent()) {
            Student student = existingStudent.get();

            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setPassword(updatedStudent.getPassword());
            student.setMatricule(updatedStudent.getMatricule());
            student.setDepartment(updatedStudent.getDepartment());

            return studentMapper.toDTO(studentRepository.save(student));
        }

        throw new IllegalArgumentException("Student with ID " + id + " does not exist.");
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}

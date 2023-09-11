package cal.projeteq3.glucose.mapper;

import cal.projeteq3.glucose.dto.StudentDTO;
import cal.projeteq3.glucose.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDTO toDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId(student.getUserID());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setMatricule(student.getMatricule());
        studentDTO.setDepartment(student.getDepartment());

        return studentDTO;
    }

    public Student toEntity(StudentDTO studentDTO) {
        Student student = new Student();

        student.setUserID(studentDTO.getId());
        student.setLastName(studentDTO.getLastName());
        student.setFirstName(studentDTO.getFirstName());
        student.setEmail(studentDTO.getEmail());
        student.setMatricule(studentDTO.getMatricule());
        student.setDepartment(studentDTO.getDepartment());

        return student;
    }

}

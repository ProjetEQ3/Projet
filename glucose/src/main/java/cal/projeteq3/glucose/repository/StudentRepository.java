package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // query methods here

}

package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public Student findByEmail(String email);
    public Student findByMatricule(String matricule);

}

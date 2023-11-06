package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.StudentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	Student findByMatricule(String matricule);

	@Query("SELECT s FROM Student s JOIN User u ON u.id = ?1 WHERE s.id = ?1")
	Optional<StudentSummary> findStudentSummaryById(Long id);

	List<Student> findAllByDepartment(Department department);

}

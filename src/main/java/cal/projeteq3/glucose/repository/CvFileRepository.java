package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvFileRepository extends JpaRepository<CvFile, Long>{
	CvFile findByStudent(Student student);
}

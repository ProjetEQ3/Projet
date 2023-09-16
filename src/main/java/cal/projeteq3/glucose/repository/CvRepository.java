package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CvRepository extends JpaRepository<CvFile, Long> {

    CvFile findByFileName(String fileName);
    List<CvFile> findAllByCvState(CvState cvState);
    List<CvFile> findAllByStudent(Student student);
    void deleteAllByStudent(Student student);
    CvFile findByStudentAndFileName(Student student, String fileName);
}

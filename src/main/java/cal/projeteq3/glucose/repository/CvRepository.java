package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.model.State;
import cal.projeteq3.glucose.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CvRepository extends JpaRepository<CvFile, Long> {

    CvFile findByFileName(String fileName);
    List<CvFile> findAllByState(State state);
    List<CvFile> findAllByStudent(Student student);
    void deleteAllByStudent(Student student);
    CvFile findByStudentAndFileName(Student student, String fileName);
}

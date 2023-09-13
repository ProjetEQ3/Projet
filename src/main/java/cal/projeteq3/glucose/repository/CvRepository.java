package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CvRepository extends JpaRepository<CvFile, Long> {

    CvFile findByFileName(String fileName);
    List<CvFile> findAllByState(State state);
    List<CvFile> findAllByStudent(Long id);
    void deleteAllByStudent(Long idStudent);
    CvFile findByStudentAndFileName(long userID, String fileName);
}

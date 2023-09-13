package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CvRepository extends JpaRepository<CvFile, Long> {

    public CvFile findByFileName(String fileName);
    public List<CvFile> findAllByState(State state);
}

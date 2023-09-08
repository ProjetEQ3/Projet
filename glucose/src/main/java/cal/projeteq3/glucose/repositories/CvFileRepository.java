package cal.projeteq3.glucose.repositories;

import cal.projeteq3.glucose.domain.CvFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface CvFileRepository extends JpaRepository<CvFile, Long>{

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM cvFile c WHERE c.serial.serial = :serial")
	boolean existsBySerial(String serial);

	@Query("SELECT c FROM cvFile c  WHERE c.serial.serial = :serial")
	Optional<CvFile> findBySerial(String serial);


}

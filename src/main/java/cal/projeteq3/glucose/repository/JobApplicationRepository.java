package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}

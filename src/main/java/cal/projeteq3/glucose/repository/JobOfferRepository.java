package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    Optional<JobOffer> findJById(Long id);

    List<JobOffer> findJobOfferByEmployer_Id(Long employerId);

    List<JobOffer> findJobOffersByEmployer(Employer employer);

    List<JobOffer> findJobOfferByJobOfferState(JobOfferState jobOfferState);

    List<JobOffer> findJobOffersByDepartment(Department department);
    List<JobOffer> findJobOffersByDepartmentAndJobOfferState(Department department, JobOfferState jobOfferState);
}

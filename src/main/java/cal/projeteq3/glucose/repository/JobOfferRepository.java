package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findJobOfferByEmployer_Id(Long employerId);

    List<JobOffer> findJobOffersByEmployer(Employer employer);

    List<JobOffer> findJobOfferByJobOfferState(JobOfferState jobOfferState);

}

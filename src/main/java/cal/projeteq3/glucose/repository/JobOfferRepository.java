package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.jobOffre.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    @Query("SELECT j FROM JobOffer j WHERE j.employer = ?1")
    List<JobOffer> findAllByEmployer(Long employerId);

}

package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Employeur;
import cal.projeteq3.glucose.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findAllByEmployeur(Employeur employeur);
}

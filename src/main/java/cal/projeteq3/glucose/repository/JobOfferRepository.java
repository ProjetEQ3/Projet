package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.jobOffre.JobOffer;
import cal.projeteq3.glucose.model.jobOffre.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

//    @Query("SELECT j FROM JobOffer j WHERE j.employer = ?1")
//    List<JobOffer> findAllByEmployer(Long employerId);

//  Pourquoi @Query ? Si JPA propose automatiquement une méthode
    List<JobOffer> findJobOfferByEmployer_Id(Long employerId);
    List<JobOffer> findJobOfferByEmployer(Employer employer);

//    @Query("SELECT j FROM JobOffer j WHERE j.jobOfferState = 'SUBMITTED'")
//    List<JobOffer> findSubmittedJobOffers();
//
//    @Query("SELECT j FROM JobOffer j WHERE j.jobOfferState = 'ACCEPTED'")
//    List<JobOffer> findAcceptedJobOffers();
//
//    @Query("SELECT j FROM JobOffer j WHERE j.jobOfferState = 'REFUSED'")
//    List<JobOffer> findRefusedJobOffers();

//    Pourquoi 3 méthodes différentes ? Sam vien  me parler (Louis) si t'as du trouble avec JPA
    List<JobOffer> findJobOfferByJobOfferState(JobOfferState jobOfferState);

}

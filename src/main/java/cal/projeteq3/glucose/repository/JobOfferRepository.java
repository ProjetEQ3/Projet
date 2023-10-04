package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findJobOfferByEmployer_Id(Long employerId);

    List<JobOffer> findJobOffersByEmployer(Employer employer);

    List<JobOffer> findJobOfferByJobOfferState(JobOfferState jobOfferState);

    List<JobOffer> findJobOffersByDepartment(Department department);
    List<JobOffer> findJobOffersByDepartmentAndJobOfferState(Department department, JobOfferState jobOfferState);

    @Query("SELECT jobOffer FROM JobOffer jobOffer JOIN jobOffer.jobApplications jobApplication WHERE jobApplication.student.id = ?1")
    List<JobOffer> findAppliedJobOffersByStudent_Id(Long studentId);

}

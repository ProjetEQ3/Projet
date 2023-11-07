package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findAllBySemester(Semester semester);

    Optional<JobOffer> findJById(Long id);

    List<JobOffer> findJobOfferByEmployer_IdAndSemester(Long employerId, Semester semester);

    List<JobOffer> findJobOffersByEmployer(Employer employer);

    List<JobOffer> findJobOfferByJobOfferStateAndSemester(JobOfferState jobOfferState, Semester semester);

    List<JobOffer> findJobOffersByDepartmentAndSemester(Department department, Semester semester);
    List<JobOffer> findJobOffersByDepartmentAndJobOfferStateAndSemester(Department department, JobOfferState jobOfferState, Semester semester);

    @Query("SELECT jobOffer FROM JobOffer jobOffer JOIN jobOffer.jobApplications jobApplication WHERE jobApplication.student.id = ?1 AND jobOffer.semester = ?2")
    List<JobOffer> findAppliedJobOffersByStudent_Id(Long studentId, Semester semester);

    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.jobOffer.employer.id = :employerId AND ja.jobApplicationState = 'SUBMITTED'")
    int countSubmittedApplicationsForEmployer(@Param("employerId") Long employerId);

}

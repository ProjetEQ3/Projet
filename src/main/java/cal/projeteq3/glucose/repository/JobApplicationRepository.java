package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    @Query("SELECT ja.appointments FROM JobApplication ja WHERE ja.id = :jobApplicationId")
    List<Appointment> findAppointmentsByJobApplicationId(@Param("jobApplicationId") Long jobApplicationId);

    Optional<JobApplication> findByJobOfferIdAndStudentId(Long jobOfferId, Long studentId);

    List<JobApplication> findAllByJobOffer_Employer_Id(Long employerId);

    List<JobApplication> findAllByStudentId(Long id);

    @Query("SELECT ja FROM JobApplication ja WHERE ja.jobApplicationState = :acceptedState AND ja.jobOffer.employer.id = :employerId")
    List<JobApplication> findJobApplicationsByJobApplicationStateAndEmployerId(@Param("acceptedState") JobApplicationState acceptedState, @Param("employerId") Long employerId);
}

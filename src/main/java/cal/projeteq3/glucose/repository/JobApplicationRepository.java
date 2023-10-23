package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    @Query("SELECT ja.appointments FROM JobApplication ja WHERE ja.id = :jobApplicationId")
    List<Appointment> findAppointmentsByJobApplicationId(@Param("jobApplicationId") Long jobApplicationId);

}

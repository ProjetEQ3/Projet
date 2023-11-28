package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.evaluation.Evaluation;
import cal.projeteq3.glucose.model.evaluation.timeSheet.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {
    Optional<TimeSheet> findByJobApplicationId(Long jobApplicationId);
}

package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation.WorkEnvironmentEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkEnvironmentEvaluationRepository extends JpaRepository<WorkEnvironmentEvaluation, Long> {
    public boolean existsByJobApplicationId(Long jobApplicationId);
}

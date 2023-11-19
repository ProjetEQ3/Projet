package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.evaluation.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long>{}

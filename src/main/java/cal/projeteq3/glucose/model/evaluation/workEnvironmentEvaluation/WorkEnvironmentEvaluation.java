package cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation;

import cal.projeteq3.glucose.model.evaluation.Evaluation;
import cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation.sections.GeneralObservations;
import cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation.sections.WorkEnvironment;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class WorkEnvironmentEvaluation extends Evaluation {
	@Embedded private WorkEnvironment workEnvironment;
	@Embedded private GeneralObservations generalObservations;
	private String comment;
	private LocalDateTime signatureDate;
}

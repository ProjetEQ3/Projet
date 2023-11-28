package cal.projeteq3.glucose.model.evaluation.internEvaluation.sections;

import cal.projeteq3.glucose.model.evaluation.enums.WelcomeInternBackStatus;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReAdmit{
	private WelcomeInternBackStatus wouldWelcomeInternBack;
	private String technicalFormationSufficient;
}

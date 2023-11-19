package cal.projeteq3.glucose.model.evaluation.section;

import cal.projeteq3.glucose.model.evaluation.enums.WelcomeInternBackStatus;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.time.LocalDateTime;

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

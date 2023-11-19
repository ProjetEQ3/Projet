package cal.projeteq3.glucose.model.evaluation.section;

import cal.projeteq3.glucose.model.evaluation.enums.InternshipPerformance;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class GlobalAppreciation{
	private InternshipPerformance skills;
	private String specificAppraisal;
	private boolean evaluationDiscussedWithIntern;
}

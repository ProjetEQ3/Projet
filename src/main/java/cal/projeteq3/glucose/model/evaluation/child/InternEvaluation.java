package cal.projeteq3.glucose.model.evaluation.child;

import cal.projeteq3.glucose.model.evaluation.Evaluation;
import cal.projeteq3.glucose.model.evaluation.section.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class InternEvaluation extends Evaluation{
	@Embedded private Productivity productivity;
	@Embedded private QualityOfWork qualityOfWork;
	@Embedded private InterpersonalSkills interpersonalSkills;
	@Embedded private PersonalSkills personalSkills;
	@Embedded private GlobalAppreciation globalAppreciation;
	@Embedded private ReAdmit reAdmit;
	private int weeklyHoursSpent;
	private String signatureName;
	private String signatureFonction;
	private LocalDateTime signatureDate;
	private String returnAddress;
}

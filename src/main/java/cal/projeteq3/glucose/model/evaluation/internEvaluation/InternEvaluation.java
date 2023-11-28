package cal.projeteq3.glucose.model.evaluation.internEvaluation;

import cal.projeteq3.glucose.model.contract.Signature;
import cal.projeteq3.glucose.model.evaluation.Evaluation;
import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import cal.projeteq3.glucose.model.evaluation.internEvaluation.sections.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public final class InternEvaluation extends Evaluation {
	@Embedded private Productivity productivity;
	@Embedded private QualityOfWork qualityOfWork;
	@Embedded private InterpersonalSkills interpersonalSkills;
	@Embedded private PersonalSkills personalSkills;
	@Embedded private GlobalAppreciation globalAppreciation;
	@Embedded private ReAdmit reAdmit;
	@OneToOne
	private Signature signature;

	public InternEvaluation(Productivity productivity, QualityOfWork qualityOfWork, InterpersonalSkills interpersonalSkills, PersonalSkills personalSkills, GlobalAppreciation globalAppreciation, ReAdmit reAdmit) {
		this.productivity = productivity;
		this.qualityOfWork = qualityOfWork;
		this.interpersonalSkills = interpersonalSkills;
		this.personalSkills = personalSkills;
		this.globalAppreciation = globalAppreciation;
		this.reAdmit = reAdmit;
	}
}

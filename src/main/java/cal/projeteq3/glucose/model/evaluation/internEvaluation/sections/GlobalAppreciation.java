package cal.projeteq3.glucose.model.evaluation.internEvaluation.sections;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import cal.projeteq3.glucose.model.evaluation.enums.InternshipPerformance;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
public final class GlobalAppreciation{
	private static final String TITLE = "APPRÉCIATION GLOBALE DU STAGIAIRE";
	private static final String[] GLOBAL_APPRECIATION_QUESTIONS = {
		"Les habiletés démontrées dépassent de beaucoup les attentes",
		"Les habiletés démontrées dépassent les attentes",
		"Les habiletés démontrées répondent pleinement aux attentes",
		"Les habiletés démontrées répondent partiellement aux attentes",
		"Les habiletés démontrées ne répondent pas aux attentes"
	};
	private InternshipPerformance skills;
	private String specificAppraisal;
	private boolean evaluationDiscussedWithIntern;
	@Column(name = "global_appreciation_comment")
	private String comment;

	public GlobalAppreciation(InternshipPerformance internshipPerformance, String specificAppraisal, boolean evaluationDiscussedWithIntern) {
		this.skills = internshipPerformance;
		this.specificAppraisal = specificAppraisal;
		this.evaluationDiscussedWithIntern = evaluationDiscussedWithIntern;
		this.comment = comment;
	}
}

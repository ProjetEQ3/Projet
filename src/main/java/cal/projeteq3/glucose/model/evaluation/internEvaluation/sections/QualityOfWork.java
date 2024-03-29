package cal.projeteq3.glucose.model.evaluation.internEvaluation.sections;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.List;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class QualityOfWork{
	private static final String TITLE = "QUALITÉ DU TRAVAIL";
	private static final String SUBTITLE = "Capacité de s’acquitter des tâches sous sa responsabilité en s’imposant " +
		"personnellement des normes de qualité";
	private static final String[] QUALITY_OF_WORK_QUESTIONS = {
		"respecter les mandats qui lui ont été confiés",
		"porter attention aux détails dans la réalisation de ses tâche",
		"vérifier son travail, s’assurer que rien n’a été oublié",
		"rechercher des occasions de se perfectionner",
		"faire une bonne analyse des problèmes rencontrés"
	};
	private AgreementLevel respect_mandates;
	private AgreementLevel attention_to_details;
	private AgreementLevel verify_work;
	private AgreementLevel look_for_opportunities_to_improve;
	private AgreementLevel good_analysis_of_problems;
	@Column(name = "quality_of_work_comment")
	private String comment;

	public QualityOfWork(List<AgreementLevel> agreementLevels, String comment) {
		this.respect_mandates = agreementLevels.get(0);
		this.attention_to_details = agreementLevels.get(1);
		this.verify_work = agreementLevels.get(2);
		this.look_for_opportunities_to_improve = agreementLevels.get(3);
		this.good_analysis_of_problems = agreementLevels.get(4);
		this.comment = comment;
	}
}

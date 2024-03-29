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
public final class Productivity{
	private static final String TITLE = "Productivité";
	private static final String SUBTITLE = "Capacité d’optimiser son rendement au travail";
	private static final String[] PRODUCTIVITY_QUESTIONS = {
		"planifier et organiser son travail de façon efficace",
		"comprendre rapidement les directives relatives à son travail",
		"maintenir un rythme de travail soutenu",
		"établir ses priorités",
		"respecter ses échéanciers"
	};
	private AgreementLevel plan_and_organize_work;
	private AgreementLevel understand_directives;
	private AgreementLevel maintain_sustained_work_rhythm;
	private AgreementLevel establish_priorities;
	private AgreementLevel respect_deadlines;
	@Column(name = "productivity_comment")
	private String comment;

	public Productivity(List<AgreementLevel> agreementLevels, String comment) {
		this.plan_and_organize_work = agreementLevels.get(0);
		this.understand_directives = agreementLevels.get(1);
		this.maintain_sustained_work_rhythm = agreementLevels.get(2);
		this.establish_priorities = agreementLevels.get(3);
		this.respect_deadlines = agreementLevels.get(4);
		this.comment = comment;
	}
}

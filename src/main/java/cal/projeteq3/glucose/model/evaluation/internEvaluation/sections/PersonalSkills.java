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
public class PersonalSkills{
	private static final String TITLE = "HABILETÉS PERSONNELLES";
	private static final String SUBTITLE = "Capacité de faire preuve d’attitudes ou de comportements matures et responsables";
	private static final String[] PERSONAL_SKILLS_QUESTIONS = {
		"démontrer de l’intérêt et de la motivation au travail",
		"exprimer clairement ses idées",
		"faire preuve d’initiative",
		"travailler de façon sécuritaire",
		"démontrer un bon sens des responsabilités ne requérant qu’un minimum de supervision",
		"être ponctuel et assidu à son travail"
	};
	private AgreementLevel show_interest_and_motivation;
	private AgreementLevel express_ideas_clearly;
	private AgreementLevel show_initiative;
	private AgreementLevel work_safely;
	private AgreementLevel show_good_sense_of_responsibility;
	private AgreementLevel be_punctual_and_attentive_to_work;
	@Column(name = "personal_skills_comment")
	private String comment;

	public PersonalSkills(List<AgreementLevel> agreementLevels, String comment) {
		this.show_interest_and_motivation = agreementLevels.get(0);
		this.express_ideas_clearly = agreementLevels.get(1);
		this.show_initiative = agreementLevels.get(2);
		this.work_safely = agreementLevels.get(3);
		this.show_good_sense_of_responsibility = agreementLevels.get(4);
		this.be_punctual_and_attentive_to_work = agreementLevels.get(5);
		this.comment = comment;
	}
}

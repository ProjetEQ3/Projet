package cal.projeteq3.glucose.model.evaluation.section;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalSkills{
	private AgreementLevel show_interest_and_motivation;
	private AgreementLevel express_ideas_clearly;
	private AgreementLevel show_initiative;
	private AgreementLevel work_safely;
	private AgreementLevel show_good_sense_of_responsibility;
	private AgreementLevel be_punctual_and_attentive_to_work;
	private String comment;
}

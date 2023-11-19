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
public final class InterpersonalSkills{
	private AgreementLevel establish_contacts;
	private AgreementLevel contribute_to_teamwork;
	private AgreementLevel adapt_to_company_culture;
	private AgreementLevel accept_constructive_criticism;
	private AgreementLevel be_respectful;
	private AgreementLevel be_a_good_listener;
	private String comment;
}

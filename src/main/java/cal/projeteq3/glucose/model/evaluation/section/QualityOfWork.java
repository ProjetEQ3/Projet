package cal.projeteq3.glucose.model.evaluation.section;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class QualityOfWork{
	private AgreementLevel respect_mandates;
	private AgreementLevel attention_to_details;
	private AgreementLevel verify_work;
	private AgreementLevel look_for_opportunities_to_improve;
	private AgreementLevel good_analysis_of_problems;
	@Column(name = "quality_of_work_comment")
	private String comment;
}

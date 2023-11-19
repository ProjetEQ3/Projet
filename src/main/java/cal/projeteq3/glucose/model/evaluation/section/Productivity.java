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
public final class Productivity{
	private AgreementLevel plan_and_organize_work;
	private AgreementLevel understand_directives;
	private AgreementLevel maintain_sustained_work_rhythm;
	private AgreementLevel establish_priorities;
	private AgreementLevel respect_deadlines;
	@Column(name = "productivity_comment")
	private String comment;
}

package cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation.sections;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class WorkEnvironment{
	private AgreementLevel tasks;
	private AgreementLevel integration;
	private AgreementLevel time;
	private AgreementLevel hygieneAndSecurity;
	private AgreementLevel environmentIsPleasant;
	private AgreementLevel publicTransport;
	private AgreementLevel interestingSalary;
	private float salary;
	private AgreementLevel communication;
	private AgreementLevel adequateEquipment;
	private AgreementLevel acceptableWorkVolume;
}

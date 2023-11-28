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
	private AgreementLevel taskConformity;
	private AgreementLevel welcomeMeasures;
	private AgreementLevel sufficientSupervision;
	private AgreementLevel safetyCompliance;
	private AgreementLevel positiveAtmosphere;
	private AgreementLevel competitiveSalary;
	private AgreementLevel effectiveCommunication;
	private AgreementLevel adequateEquipment;
	private AgreementLevel reasonableWorkload;
}

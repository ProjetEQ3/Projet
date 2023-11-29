package cal.projeteq3.glucose.dto.evaluation;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkEnvironmentDTO{
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

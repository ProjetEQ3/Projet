package cal.projeteq3.glucose.dto.evaluation;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import cal.projeteq3.glucose.model.evaluation.enums.InternshipPerformance;
import cal.projeteq3.glucose.model.evaluation.enums.WelcomeInternBackStatus;
import cal.projeteq3.glucose.model.evaluation.internEvaluation.InternEvaluation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternEvaluationDTO {
    private List<SectionDTO> sections;
}

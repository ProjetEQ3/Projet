package cal.projeteq3.glucose.dto.evaluation;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import cal.projeteq3.glucose.model.evaluation.enums.InternshipPerformance;
import cal.projeteq3.glucose.model.evaluation.enums.WelcomeInternBackStatus;
import lombok.Data;

import java.util.List;

@Data
public class SectionDTO {
    private List<AgreementLevel> agreementLevels;
    private InternshipPerformance internshipPerformance;
    private WelcomeInternBackStatus welcomeInternBackStatus;
    private boolean discusssedWithIntern;
    private String comment;
}

package cal.projeteq3.glucose.model.evaluation.section;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralObservations{
	private Boolean isFirstInternshipPreferred;
	private Boolean isSecondInternshipPreferred;
	private Integer numberOfInternsOpenTo;
	private Boolean wouldHostSameInternAgain;
	private Map<LocalDateTime, LocalDateTime> variableWorkShifts;
}
